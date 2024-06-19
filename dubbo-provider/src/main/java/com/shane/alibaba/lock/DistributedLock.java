package com.shane.alibaba.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DistributedLock {

    @Value("${zookeeper.list}")
    private final String connetString = "192.168.31.49:2181,192.168.31.49:2281,192.168.31.49:2381";

    private final int sessionTime = 2000;

    private final ZooKeeper zk;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private final CountDownLatch waitLatch = new CountDownLatch(1);

    private String waitPath;

//    private String mode;

    public DistributedLock() throws IOException, InterruptedException, KeeperException {
        zk = new ZooKeeper(connetString, sessionTime, watchedEvent -> {
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }

            if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)) {
                waitLatch.countDown();
            }
        });

        countDownLatch.await();

        Stat stat = zk.exists("/locks", false);

        if (stat == null) {
            zk.create("/locks", "locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public String lock() {
        try {
            String mode = zk.create("/locks/" + "seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = zk.getChildren("/locks", false);
            if (children.size() > 1) {
                Collections.sort(children);
                String thisNode = mode.substring("/locks/".length());
                int index = children.indexOf(thisNode);
                if (index == -1) {
                    System.out.println("error data");
                } else if (index > 0) {
                    waitPath = "/locks/" + children.get(index - 1);
                    zk.getData(waitPath, true, null);
                    waitLatch.await();
                }
            }
            return mode;
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void unlock(String mode) {
        try {
            zk.delete(mode, 0);

        } catch (InterruptedException | KeeperException e) {
            throw new RuntimeException(e);
        }
    }
}
