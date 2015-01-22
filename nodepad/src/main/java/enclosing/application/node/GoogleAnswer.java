/*
 * Created on 2006/01/16
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node;



/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GoogleAnswer {
    String[] answers = null;
    

    public String[] getAnswers() {
        return answers;
    }
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
    public GoogleAnswer(NodeObserver nodeObserver,String[] directives){
//        GoogleSearch googleSearch = new GoogleSearch();
    /** @todo not implemented */    
        
    }
    public GoogleAnswer(String onequery){
        try {
//            GoogleSearch googleSearch = new GoogleSearch();
//            googleSearch.setKey("f4yo24JQFHKzGeNyJSDpwgnY/mb9jDfb");
//            googleSearch.setQueryString(onequery);
//            googleSearch.setMaxResults(5);
//            GoogleSearchResult googleSearchResult = googleSearch.doSearch();
//            GoogleSearchResultElement[] resultElements = googleSearchResult.getResultElements();
////            this.answers =new String[resultElements.length];
//            this.answers = new String[resultElements.length];
//            for(int i = 0;i < resultElements.length;i++){
//                this.answers[i] = resultElements[i].getURL() + "\r\n";
//                this.answers[i] = this.answers[i] + resultElements[i].getSummary();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openAllWithBrowser(){
        for(int i = 0;i < this.answers.length;i++){
            HttpBrowser browser = new HttpBrowser(this.answers[i]);
        }
    }
}
