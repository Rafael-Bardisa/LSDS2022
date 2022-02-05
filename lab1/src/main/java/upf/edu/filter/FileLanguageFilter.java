package upf.edu.filter;

import upf.edu.parser.SimplifiedTweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;

public class FileLanguageFilter implements LanguageFilter{

  private String inputFile;
  private String outputFile;

  public void fileLanguageFilter(String inputfile, String outputfile){    //constructor
    this.inputFile = inputfile;
    this.outputFile = outputfile;
  }

  public void filterLanguage(String language) throws Exception {
    // check if each line in the input file is of the language
    // indicated and append those in that langauge to output file
    FileReader fr = new FileReader(this.inputFile.toString());
    BufferedReader br = new BufferedReader(fr);
    BufferedWriter bw = null;
    try {
      String line = br.readLine();
      while (line!=null) {
        FileWriter wr = new FileWriter(this.outputFile.toString());
        bw = new BufferedWriter(wr);
        // call simplified tweet method to convert into type
        Optional<SimplifiedTweet> st = SimplifiedTweet.fromJson(line); //
        if (st.isPresent() && st.get().getLanguage().equals(language)) { // maybe we need a method to get the language
          bw.write(st.toString());
        }
        line = br.readLine();
        System.out.println("hola");
      }
    } finally {
      bw.close();// close resources
      br.close();
    }
  }
}
