package upf.edu.filter;

import upf.edu.parser.SimplifiedTweet;

import java.io.*;
import java.util.Optional;

public class FileLanguageFilter implements LanguageFilter{

  private final String inputFile;
  private final String outputFile;

  public FileLanguageFilter(String inputfile, String outputfile){    //constructor

    /* TODO estos strings representa que estaran en el mismo directorio que el jar
         eg usando ../target/fat.jar, new FileReader(inputfile) leera de ../target/inputfile
         y new FileWriter(outputfile) escribe ../target/outputfile, asi que los strings deben
         incluir sus extensiones
     */
    this.inputFile = inputfile;
    this.outputFile = outputfile;
  }

  public void filterLanguage(String language) throws IOException {
    // check if each line in the input file is of the language
    // indicated and append those in that langauge to output file
    try (BufferedReader br = new BufferedReader(new FileReader(this.inputFile)); BufferedWriter bw = new BufferedWriter(new FileWriter(this.outputFile, true))) {
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        // call simplified tweet method to convert into type
        Optional<SimplifiedTweet> st = SimplifiedTweet.fromJson(line); //
        if (st.isPresent() && st.get().getLanguage().equals(language)) { // maybe we need a method to get the language
          bw.write(st.get().toString());
        }
        // br.readLine();
      }
    }
  }
}
