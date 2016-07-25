package framekwork;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class IMAPHandler {

    private String folderSpace = "-";

    public IMAPHandler() {

    }

    public boolean transferMailAccount(MailAccountMap mMap) {

        try {

            Properties props = System.getProperties();
            // props.setProperty("mail.selectedwork.de", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            Store storeFrom = session.getStore("imaps");
            storeFrom.connect(mMap.getFrom().getImapServer(), mMap.getFrom().getImapLoginName(), mMap.getFrom().getImapLoginPassword());

            Store storeTo = session.getStore("imaps");
            storeTo.connect(mMap.getTo().getImapServer(), mMap.getTo().getImapLoginName(), mMap.getTo().getImapLoginPassword());

            transferAccount(storeFrom, storeTo, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    private void transferAccount(Store from, Store to, String fromFolderName) throws Exception {

        Folder tmpFolder = null;

        if (fromFolderName != null) {
            if (!from.getFolder(fromFolderName).exists()) {
                tmpFolder = findFolder(from.getDefaultFolder().list(), fromFolderName);
            } else {
                tmpFolder = from.getFolder(fromFolderName);
            }
        } else {
            tmpFolder = from.getDefaultFolder();
        }

        if (tmpFolder != null) {
            if (tmpFolder.getName() != "") {
                System.out.println("F> " + tmpFolder.getName());
                printMessages(tmpFolder);
                Folder tmp = createFolder(to, tmpFolder.getFullName());
                transferMessages(tmpFolder, tmp);
            } else {
                System.out.println("R> DefaultFolder");
            }

            transferAccount(to, tmpFolder);
        } else {
            System.out.println(fromFolderName + ": Folder does exist");
        }
    }

    private Folder findFolder(Folder[] fd, String fromFolderName) throws MessagingException {

        for (int i = 0; i < fd.length; i++) {

            if (fd[i].getName().equals(fromFolderName)) {
                return fd[i];
            }

            Folder tmp = findFolder(fd[i].list(), fromFolderName);
            if (tmp != null) {
                return tmp;
            }

        }

        return null;
    }

    private void transferAccount(Store to, Folder folder) throws Exception {
        Folder[] folderList = folder.list();

        if (folderList != null) {

            for (int i = 0; i < folderList.length; i++) {
                System.out.println("F" + folderSpace + "> " + folderList[i].getName());

                if (hasMessages(folderList[i])) {
                    printMessages(folderList[i], folderSpace);

                    transferMessages(folderList[i], createFolder(to, folderList[i].getFullName()));
                }

                if (folderList[i].list().length > 0) {
                    folderSpace += "-";
                    transferAccount(to, folderList[i]);
                }
            }
            folderSpace = "-";
        }
    }

    private boolean hasMessages(Folder f) throws MessagingException {
        f.open(Folder.READ_ONLY);
        Message message[] = f.getMessages();
        f.close(true);

        return (message.length > 0) ? true : false;
    }

    private void printMessages(Folder f) throws MessagingException {
        printMessages(f, "");
    }

    private void printMessages(Folder f, String folderSpace) throws MessagingException {
        f.open(Folder.READ_ONLY);

        Message message[] = f.getMessages();

        if (message.length > 0) {
            for (Message m : message) {
                System.out.println("M ^" + folderSpace + "> " + m.getSubject());
            }
        }

        f.close(true);
    }

    private Folder createFolder(Store s, String name) throws MessagingException {
        Folder newFolder = s.getFolder(name);

        if (!newFolder.exists()) {
            newFolder.create(Folder.HOLDS_MESSAGES);
        }

        if (!newFolder.isSubscribed()) {
            newFolder.setSubscribed(true);
        }

        return newFolder;
    }

    private boolean transferMessages(Folder folder_source, Folder folder_dest) throws Exception {
        folder_source.open(Folder.READ_ONLY);

        // Check if the source folder contains messages
        if (folder_source.getMessageCount() == 0) {
            System.out.println("source folder is empty");
            folder_source.close(false);
            return false;
        }

        // Create the destination folder if it does not exist
        if (!folder_dest.exists()) {
            folder_dest.create(Folder.HOLDS_MESSAGES);
        }

        // Message[] msgs = folder_source.getMessages(from_msg, to_msg);
        Message[] msgs = folder_source.getMessages();

        // Copy selected messages into the destination folder
        // delete
        /*
		 * folder_dest.open(Folder.READ_WRITE); Flags deleted = new
		 * Flags(Flags.Flag.DELETED); folder_dest.setFlags(msgs, deleted, true);
		 * folder_dest.close(true);
         */
        folder_source.copyMessages(msgs, folder_dest);

        System.out.println("successfully");
        folder_source.close(false);
        return true;
    }

}
