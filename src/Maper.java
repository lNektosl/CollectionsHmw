import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.FileReader;
import java.util.*;

public class Maper {
    Map<Integer, String> authors = new HashMap<>();
    int a = 0;

    public void make() throws Exception {
        String line;
        BufferedReader csvAuthors = new BufferedReader(new FileReader("author.csv"));
        while ((line = csvAuthors.readLine()) != null) {
            String[] holder = line.split(",");
            if (a == 0) a++;
            else {
                authors.put(Integer.parseInt(holder[0]), holder[1]);
            }
        }
        csvAuthors.close();
    }

    public void SetImages() throws Exception {
        String del = ("/\\:*?\"<>|");
        String line;
        a = 0;
        BufferedReader csvFile = new BufferedReader(new FileReader("book.csv"));
        while ((line = csvFile.readLine()) != null) {
            if (a == 0) a++;
            else {
                String[] holder = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//считка пути
                for (char a : del.toCharArray()) {
                    holder[1] = holder[1].replace(String.valueOf(a), "");
                }
                File file = new File("images/" + holder[4]);
                BufferedImage bImage = ImageIO.read(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                byte[] imgHolder = bos.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(imgHolder);
                BufferedImage bImage2 = ImageIO.read(bis);

                ImageIO.write(bImage2, "jpg", new File("result/img/" + authors.get(Integer.parseInt(holder[5])) + " - " + holder[1] + ".jpg"));
            }
        }
    }

    public void price() throws Exception {
        String line;
        ArrayList<Double> price = new ArrayList<>();
        a = 0;
        BufferedReader csvFile = new BufferedReader(new FileReader("book.csv"));
        while ((line = csvFile.readLine()) != null) {
            if (a == 0) a++;
            else {
                String[] holder = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//считка пути
                price.add(Double.parseDouble(holder[2]));
            }
        }
        Collections.sort(price);
        FileWriter fw = new FileWriter("result/result.txt");
        String txtHolder = "Минимальная цена - " + price.get(0) + "\nМаксимальная цена - " + price.get(price.size() - 1);
        fw.write(txtHolder);
        fw.close();
    }
}
