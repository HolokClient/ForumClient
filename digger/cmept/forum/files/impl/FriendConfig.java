package digger.cmept.forum.files.impl;

import digger.cmept.forum.forum;
import digger.cmept.forum.files.FileManager;
import digger.cmept.forum.friend.Friend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FriendConfig
        extends FileManager.CustomFile {
    public FriendConfig(String name, boolean loadOnStart) {
        super(name, loadOnStart);
    }

    @Override
    public void loadFile() {
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                if (forum.instance.friendManager == null) continue;
                forum.instance.friendManager.addFriend(name);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
            for (Friend friend : forum.instance.friendManager.getFriends()) {
                out.write(friend.getName().replace(" ", ""));
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
