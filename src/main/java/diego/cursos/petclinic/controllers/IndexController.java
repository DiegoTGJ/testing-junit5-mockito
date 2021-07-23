package diego.cursos.petclinic.controllers;

import diego.cursos.petclinic.exceptions.ValueNotFoundException;

public class IndexController {

    public String index(){

        return "index";
    }

    public String oopsHandler(){
        throw new ValueNotFoundException();
    }
}
