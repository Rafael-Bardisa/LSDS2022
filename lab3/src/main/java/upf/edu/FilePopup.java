package upf.edu;

import javax.swing.*;


public class FilePopup {
    public static String getFileFor(String description) {
        JFileChooser popup = new JFileChooser();
        popup.setDialogTitle(description);
        popup.setApproveButtonText("Use");

        return(popup.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ? popup.getSelectedFile().getAbsolutePath(): null);
    }
    /**
     *
     * Tries to handle a missing twitter properties file using built-in java file browser. Assumes user will select the correct file either as CLI argument or in the browser and properties file name to be the first argument in the list
     * @param programArgs program arguments
     * @param expectedLength expected length of the program arguments
     * @return the name of the selected file
     */
    public static String propertyPath(String[] programArgs, int expectedLength){
        return programArgs.length < expectedLength ? FilePopup.getFileFor("Credentials") : programArgs[0];
    }
}
