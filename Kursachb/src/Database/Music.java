package Database;

public class Music {
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Music(String title, String musician, String path, String style) {
        Title = title;
        Musician = musician;
        this.path = path;
        Style = style;
    }

    public String getMusician() {
        return Musician;
    }
    public void setMusician(String musician) {
        Musician = musician;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public String getFullName(){
        String fullName = null;
        if (getMusician()!=null){
             fullName = getMusician()+" - "+ getTitle();
        }
        else{
            fullName = getTitle();
        }

         return fullName;
    }

    private String Title;
    private String Musician;
    private String path;
    private String Style;
}
