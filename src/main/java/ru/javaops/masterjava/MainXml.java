package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.newschema.Group;
import ru.javaops.masterjava.xml.newschema.Payload;
import ru.javaops.masterjava.xml.newschema.Project;
import ru.javaops.masterjava.xml.newschema.User;
import ru.javaops.masterjava.xml.projects.ObjectFactory;
import ru.javaops.masterjava.xml.projects.Projects;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import sun.plugin.navig.motif.OJIPlugin;

import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;
import java.util.stream.Collectors;

/**
 * Created by konst on 09.03.17.
 */
public class MainXml {

    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);


    public static void main(String[] args) throws IOException {
        System.out.println("Start!");
        final String input = "<Projects xmlns=\"http://javaops.ru\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://javaops.ru projects.xsd\">\n" +
                "    <name>topjava</name>\n" +
                "</Projects>";

        new MainXml().run(input);
    }

    public void run(final String input) throws IOException {
        final String nameProject = parse(input);
        System.out.println("nameProject = " + nameProject);

        final Payload payload = parse(Resources.getResource("data/payload.xml").openStream());
        if (payload == null)
            return;

        List<JAXBElement<List<Object>>> user = payload.getProjects().getProject().get(0).getGroup().get(0).getUsers().getUser();
        Project project = payload.getProjects().getProject()
                .stream()
                .filter(t->t.getName().equalsIgnoreCase(nameProject))
                .findFirst()
                .orElse(null);
        if (project == null)
            return;

        List<String> users = project.getGroup().stream()
                .flatMap(t->((Group.Users)(t.getUsers() == null ? new Group.Users() : t.getUsers())).getUser().stream())
                .flatMap(t->t.getValue().stream())
                .map(t->(User)t)
                .map(User::getFullName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("users = " + users);

    }

    public Payload parse(InputStream stream) {
        JaxbParser parser = new JaxbParser(ru.javaops.masterjava.xml.newschema.ObjectFactory.class);
        parser.setSchema(Schemas.ofClasspath("payload.xsd"));
        try {
            return parser.unmarshal(stream);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parse(String input) {
        try {
            Projects project = JAXB_PARSER.unmarshal(input);
            return project.getName();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "";
        }
    }
}
