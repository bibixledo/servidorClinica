

public class FabricaPessoa {
    public static Pessoa getPessoa(String codigo){
        Pessoa retorno = null;
        IBancoDados db = new Persistencia();
        if(db.hasCliente(codigo)){
            retorno = db.getCliente(codigo);
        }else if(db.hasFuncionario(codigo)){
            retorno = db.getFuncionario(codigo);
        }
        return retorno;
    }
    
    public static Pessoa getPessoaNome(String nome){
        IBancoDados db = new Persistencia();
        Pessoa retorno = db.getPessoaNome(nome);
        String codigo = retorno.getNome();
        if(db.hasCliente(codigo)){
            retorno = db.getCliente(codigo);
        }else if(db.hasFuncionario(codigo)){
            retorno = db.getFuncionario(codigo);
        }
        return retorno;
    }
}
