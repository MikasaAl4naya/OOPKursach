package Database;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class MusicList {
    public static ArrayList<Music> list = new ArrayList<>();
    public static ArrayList<Music> MusicPlaylist = new ArrayList<>();
    public void add(Music music)
    {
        list.add(music);
    }
    public void addTPlay(Music music)
    {
        MusicPlaylist.add(music);
    }

    public URI returnPath(int i)
    {
       File f=new File(MusicPlaylist.get(i).getStyle());
        return f.toURI();
    }
    public void clear()
    {
        list.clear();
        MusicPlaylist.clear();
    }

    public String returnFN(int i)
    {
        String FN = null;
        if (MusicPlaylist.get(i).getMusician()!=null) {
             FN = MusicPlaylist.get(i).getMusician() + " - " + MusicPlaylist.get(i).getTitle();
        }
        else
        {
            FN=MusicPlaylist.get(i).getTitle();
        }
        return FN;
    }

}
