package diego.cursos.controllers;

import diego.cursos.exceptions.ValueNotFoundException;

public class IndexController {

    public String index(){

        return "index";
    }

    public String oopsHandler(){
        throw new ValueNotFoundException();
    }
}
