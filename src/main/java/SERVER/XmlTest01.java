package SERVER;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlTest01 {
    /*解析sax流程*/
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //1 获取解析工厂
        SAXParserFactory factory=SAXParserFactory.newInstance();
        //2 从解析工厂获取解析器
        SAXParser parser=factory.newSAXParser();
        //3 加载文档Document注册处理器
        //4 编写处理器
        PHandler handler=new PHandler();
        //5 解析
        parser.parse(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("p.xml"),handler);

        List<Person> persons=handler.getPersons();
        for (Person p:persons){
            System.out.println(p.getName()+"-->"+p.getAge());
        }
    }

}
class PHandler extends DefaultHandler{
    private List<Person> persons;
    private Person person;
    private String tag;//存储操作的标签
    @Override
    public void startDocument() throws SAXException{
        System.out.println("解析文档开始");
        persons=new ArrayList<>();
    }
    @Override
    public  void startElement(String uri, String localName, String qName, Attributes attributes){
        System.out.println(qName+"解析开始");
        if (null!=qName){
            tag=qName;
        }
        if (qName.equals("person")){
             person=new Person();
        }

    }
    @Override
    public void characters(char[] ch,int start,int length)throws SAXException{
        String contents=new String(ch,start,length).trim();
        System.out.println("内容为："+contents);
        if (null!=tag) {
            if (tag.equals("name")) {
                person.setName(contents);
            } else if (tag.equals("age")) {
                if (contents.length() > 0)
                    person.setAge(Integer.valueOf(contents));
            }
        }
    }

    @Override
    public void endElement(String uri,String localname,String qName){
        System.out.println(qName+"解析结束");
        if (null!=qName) {
            if (qName.equals("person")) {
                persons.add(person);
            }
            tag=null;
        }
    }
    @Override
    public void endDocument() throws SAXException{
        System.out.println("解析文档结束");
    }

    public List<Person> getPersons() {
        return persons;
    }
}
