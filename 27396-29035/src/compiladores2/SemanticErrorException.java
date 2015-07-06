/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores2;

/**
 *
 * @author Satrix
 */
class SemanticErrorException extends Exception {

    public SemanticErrorException() {
        super();
    }
    
    public SemanticErrorException(String s){
        super(s);
    }
    
}
