import java.lang.String;
import java.lang.Character;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;


public class WebToPigLatin
{
    private Formatter output;
    private String outfile;
    private char[] array;
    private int theSize;
    private int theCapacity;
  
    public WebToPigLatin()
{
    theSize = 0;
    theCapacity = 0;
}

public WebToPigLatin(int size, String out)
{
    theSize = 0;
    theCapacity = size;
    outfile = new String(out);
    array = new char[theCapacity];
}

public void addElement(int w)
{
char c = (char)w;
array[theSize] = c;
theSize++;
}

public void Insert(char c, int index)
{
if(theSize == theCapacity)
{
    theCapacity *= 2;
}
char [] newarray = new char[theCapacity];

int i;
for(i = 0;i < index; i++)
{
    newarray[i] = array[i];
}

newarray[index] = c;
theSize++;

int k = index;
for(i = index+1;i < theSize; i++)
{
newarray[i] = array[k];
k++;
}

array = new char[theCapacity];


for(i = 0; i < theSize; i++)
{
array[i] = newarray[i];
}
}

public void Delete(int index)
{
char [] newarray = new char[theCapacity];
int i;
for(i = 0; i < index ; i++)
{
    newarray[i] = array[i];
}

for(i = index; i < theSize; i++)
{
    newarray[i] = array[i+1];
}
theSize--;
array = new char[theCapacity];

for(i = 0; i < theSize; i++)
{
    array[i] = newarray[i];
}
}

    public void openFile()
    {
       try
       {
          output = new Formatter( outfile );
       }
       catch ( SecurityException securityException )
       {
          System.out.println(
             "You do not have write access to this file." );
          System.exit( 1 );
       } 
       catch ( FileNotFoundException filesNotFoundException )
       {
          System.out.println( "Error creating file." );
          System.exit( 1 );
       } 
    } 

    public void closefile()
    {
        if(output!=null)
        {
            output.close();
        }
    }

    public void addRecords()
    {
        
        try 
        {
            FileWriter mywriter = new FileWriter(outfile);
            for(int i = 0;i < theSize; i++)
            {
             mywriter.write(array[i]);
            }
             mywriter.close();
        } 
        catch(IOException ioException)
        {
            
            System.out.println("Error");
            System.exit(1);
        }
    } 

public boolean CheckV(char c)
{
    if(c == 'A'){return true;}
    else if(c == 'E'){return true;}
    else if(c == 'I'){return true;}
    else if(c == 'O'){return true;}
    else if(c == 'U'){return true;}
    else if(c == 'a'){return true;}
    else if(c == 'e'){return true;}
    else if(c == 'i'){return true;}
    else if(c == 'o'){return true;}
    else if(c == 'u'){return true;}

    return false;
}

public boolean CheckC(char c)
{
    if(c != 'A' && c != 'E' && c != 'I' && c != 'O'
    && c != 'U' && c != 'a' && c != 'e' && c != 'i'
    && c != 'o' && c != 'u')
    {
    if(c > 64 && c < 91){return true;}
    else if( c > 96 && c < 123){return true;}
    }

    return false;
}

public boolean EndWord(char c)
{
    if(c > 31 && c < 48 && c != 39){return true;}
    else if(c > 57 && c < 65){return true;}
    else if(c > 90 && c < 97){return true;}
    else if(c > 122 && c < 127){return true;}
    else if(c == '\r'){return true;}
    else if(c == '\n'){ return true;}
    
    

    return false;

}

public boolean Capital(char c)
{
    if(c > 64 && c < 91){return true;}
    return false;
}

public void translate()
{
    int i, j ,k,p = 0, l = 0;
    boolean word = false, cap = false;
    
    for(i =0; i < theSize; i++)
    {
        if(array[i] == '<')
        {
            for(j = i; j < theSize-1; j++)
            {
                if(array[j] == '>')
                {
                    i = j;
                    break;
                }
            }
        }

        if(array[i] == '&' && array[i+1] != ' ')
        {
            for(j = i; j < theSize; j++)
            {
                if(array[j] == ';')
                {
                    i = j;
                    break;
                }
            }
        }

        if(EndWord(array[i]))
        {
            p = i;
            for(j = i + 1; j < theSize;j++)
            {
            if(EndWord(array[j]))
                {
                    l = j;
                    break;
                }
            }

 for(k = p+1; k < l;k++)
        {
            if(CheckV(array[k]))
            {
                word = true;
            }
        }

        for(k = p + 2; k< l; k++)
        {
            if(array[k] == 'y' || array[k] == 'Y')
            {
                word = true;
            }
        }

        }
        
       

if(word && CheckV(array[p+1]))
{
Insert('w', l);
l++;
Insert('a', l);
l++;
Insert('y', l);

word = false;
    }
    else if(word && CheckC(array[p+1]) && array[p+2] != ' ')
    {
        if(Capital(array[p+1]))
        {
            array[p+1] = Character.toLowerCase(array[p+1]);
            cap = true;
        }
        else{cap = false;}
        
        {
            
        }
        Insert(array[p+1], l);
            Delete(p+1);

        while(CheckC(array[p+1]) && array[p+1] != 'y' && array[p+1]!= 'Y')
        {
            Insert(array[p+1], l);
            Delete(p+1);
        }

        if(cap)
        {
            array[p+1] = Character.toUpperCase(array[p+1]);
        }

        Insert('a', l);
        l++;
        Insert('y', l);

        word = false;
    }       
    }
}



    public static void main(String[] args )
    {

int size = 2;

if(args.length != 2)
{
    System.out.println("Must have two command-line parameters");
    System.exit(1);
}


try {
    File myObj = new File(args[0]);
    FileReader reader = new FileReader(myObj);
    while (reader.ready()) 
    {
        reader.read();
     size++;
    }
    reader.close();
  } catch (FileNotFoundException e) 
  {
    System.out.println("An error occurred4.");
  }
  catch(IOException ioException)
  {
      System.out.println("An error occurred3.");
      System.exit(1);
  }

    WebToPigLatin t = new WebToPigLatin(size, args[1]);

    try {
    File myObj = new File(args[0]);
    FileReader reader = new FileReader(myObj);
    t.addElement(32);
    while (reader.ready()) 
    {
        t.addElement(reader.read());
     
    }
    t.addElement(32);
    reader.close();
  } catch (FileNotFoundException e) 
  {
    System.out.println("An error occurred2.");
  }
  catch(IOException ioException)
  {
      System.out.println("An error occurred1.");
      System.exit(1);
  }

t.openFile();
t.translate();
t.addRecords();
t.closefile();


        
        

        


    
}
}