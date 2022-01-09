package graph;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import common.LoadJson;
import graph.helper.Permission;

public class DirectedGraphImpl {
    public static void main(String[] args) throws IOException {
        try {
        final List<Permission> permissions = LoadJson.loadResource("NaryTest.json", new TypeReference<List<Permission>>(){});
        System.out.println(permissions.stream().map(Permission::getName).collect(Collectors.joining(";")));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
