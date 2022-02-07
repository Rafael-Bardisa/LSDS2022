package upf.edu.filter;

import java.io.IOException;

public interface LanguageFilter {

  /**
   * Process
   * @param language
   * @return
   */

  int filterLanguage(String language) throws IOException;
}
