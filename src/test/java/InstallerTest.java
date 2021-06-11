import java.io.File;
import java.io.IOException;

public class InstallerTest {

    public static void main(String[] args) throws IOException {
        String installer = "C:\\Users\\quent\\Desktop\\eden\\build\\eden-setup-1.0.0";
        ProcessBuilder process = new ProcessBuilder(installer, "/VERYSILENT", "/MERGETASKS=\"desktopicon,postinstall\"");
        process.directory(new File(new File(installer).getParent()));
        process.start();
    }

}
