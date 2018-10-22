

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persistencia implements IBancoDados{
    
    private Connection con;
    
    public Persistencia(){
        try {
            con = DriverManager.getConnection("jdbc:sqlite:clinica");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private ResultSet getRS(String query){
        ResultSet rs = null;
        try{
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    
    @Override
    public String[] getTelefone(String codigo){
        String[] telefone = null;
        try {
            ResultSet trs = getRS("SELECT tel FROM telefone WHERE cod = '"+codigo+"'");
            ResultSet auxRs = trs;
            int cont = 0;
            List<String> aux = new ArrayList();
            while(auxRs.next()){
                 aux.add(trs.getString("tel"));
                    cont++;
                }
                String[] listaTel = new String[cont];
                for(int i = 0;i < cont;i++){
                    listaTel[i] = aux.get(i);
                }
                telefone = listaTel;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return telefone;
    }

    @Override
    public Iterator<Cliente> getListaCliente() {
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        try{
            ResultSet rs = getRS("SELECT * FROM cliente");
            while(rs.next()){
                Cliente novo = new Cliente();
                novo.setCodigo(rs.getString("cod"));
                novo.setNome(rs.getString("nome"));
                novo.setCpf(rs.getString("cpf"));
                novo.setDataNascimento(rs.getString("dnasc"));
                novo.setEndereco(rs.getString("ende"));
                novo.setTelefone(getTelefone(novo.getCodigo()));
                listaCliente.add(novo);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return listaCliente.iterator();
    }
    

    //Metodos de persitencia para fabrica de Pessoas
    @Override
    public Cliente getCliente(String codigo) {
        Cliente retorno = null;
        try{
            ResultSet rs = getRS("SELECT * FROM cliente WHERE cod = '"+codigo+"'");
            retorno = new Cliente();
            retorno.setCodigo(codigo);
            retorno.setNome(rs.getString("nome"));
            retorno.setCpf(rs.getString("cpf"));
            retorno.setEndereco(rs.getString("ende"));
            retorno.setDataNascimento(rs.getString("dnasc"));
            retorno.setTelefone(getTelefone(codigo));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno;
    }
    
    @Override
    public Funcionario getFuncionario(String codigo) {
        Funcionario retorno = null;
        try{
            ResultSet rs = getRS("SELECT * FROM funcionario WHERE cod = '"+codigo+"'");
            retorno = new Funcionario();
            retorno.setCodigo(codigo);
            retorno.setNome(rs.getString("nome"));
            retorno.setCpf(rs.getString("cpf"));
            retorno.setDataNascimento(rs.getString("dnasc"));
            retorno.setTelefone(getTelefone(codigo));
            retorno.setEndereco(rs.getString("ende"));
            retorno.setMatricula(rs.getString("matricula"));
            retorno.setCrm(rs.getString("crn"));
            retorno.setDataInicio(rs.getString("dinicio"));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno;
    }


    @Override
    public boolean hasCliente(String codigo) {
        try {
            ResultSet rs = getRS("SELECT * FROM cliente WHERE cod = '"+codigo+"'");
            return rs.first();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;    }

    @Override
    public boolean hasFuncionario(String codigo) {
        try {
            ResultSet rs = getRS("SELECT * FROM funcionario WHERE cod = '"+codigo+"'");
            return rs.first();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;    }

    @Override
    public void setCliente(Cliente novo) {
        try {
            if(novo.getCodigo() == null){
                String cliente = "'"+novo.getNome()+"','"+novo.getCpf()+"','"+novo.getEndereco()+"','"+novo.getDataNascimento()+"'";
                String update = "INSERT INTO pessoa(nome,cpf,ende,dnasc) VALUES("+cliente+")";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
                String novoCod = stmt.executeQuery("SELECT cod FROM pessoa WHERE nome = '"+novo.getNome()+"'").getString("cod");
                cliente = "'"+novoCod+"'," + cliente;
                update = "INSERT INTO cliente(cod,nome,cpf,ende,dnasc) VALUES("+cliente+")";
                stmt.executeUpdate(update);
            }else{
                String cliente = "nome = '"+novo.getNome()+"',cpf = '"+novo.getCpf()+"',ende = '"+novo.getEndereco()+"',dnasc = '"+novo.getDataNascimento()+"'";
                String update = "UPDATE cliente SET "+cliente+" WHERE cod = '"+novo.getCodigo()+"'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void setFuncionario(Funcionario novo) {
        try {
            if(novo.getCodigo() == null){
                String cliente = "'"+novo.getNome()+"','"+novo.getCpf()+"','"+novo.getEndereco()+"','"+novo.getDataNascimento()+"'";
                String update = "INSERT INTO pessoa(nome,cpf,ende,dnasc) VALUES("+cliente+")";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
                String novoCod = stmt.executeQuery("SELECT cod FROM pessoa WHERE nome = '"+novo.getNome()+"'").getString("cod");
                cliente = "'"+novoCod+"'," + cliente;
                update = "INSERT INTO funcionario(cod,nome,cpf,ende,dnasc) VALUES("+cliente+")";
                stmt.executeUpdate(update);
            }else{
                String cliente = "nome = '"+novo.getNome()+"',cpf = '"+novo.getCpf()+"',ende = '"+novo.getEndereco()+"',dnasc = '"+novo.getDataNascimento()+"'";
                String update = "UPDATE pessoa SET "+cliente+" WHERE cod = '"+novo.getCodigo()+"'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    @Override
    public Consulta getConsulta(String codigo) {
            Consulta retorno = null;
        try {
            ResultSet rs = getRS("SELECT * FROM consulta WHERE codconsulta = '"+codigo+"'");
            retorno = new Consulta();
            retorno.setInicio(rs.getString("hinicio"));
            retorno.setTermino(rs.getString("hfim"));
            retorno.setCodigo(codigo);
            String codC = rs.getString("codC");
            String codF = rs.getString("codF");
            rs.close();
            retorno.setPaciente(getCliente(codC));
            retorno.setProfissional(getFuncionario(codF));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Iterator<Consulta> getListaConsulta() {
        List<Consulta> retorno = new ArrayList<Consulta>();
        try {
            List<String> codigos = new ArrayList<String>();
            ResultSet rs = getRS("SELECT codconsulta FROM consulta");
            while(rs.next()){
                codigos.add(rs.getString("codconsulta"));
            }
            Iterator<String> it = codigos.iterator();
            while(it.hasNext()){
                Consulta novo = getConsulta(it.next());
                retorno.add(novo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return retorno.iterator();
    }

    @Override
    public Cliente getClienteNome(String nome) {
        Cliente retorno = null;
        try{
            ResultSet rs = getRS("SELECT * FROM cliente WHERE nome = '"+nome+"'");
            retorno = new Cliente();
            retorno.setNome(nome);
            retorno.setCodigo(rs.getString("cod"));
            retorno.setCpf(rs.getString("cpf"));
            retorno.setDataNascimento(rs.getString("dnasc"));
            retorno.setEndereco(rs.getString("ende"));
            retorno.setTelefone(getTelefone(retorno.getCodigo()));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno;    
    }

    @Override
    public Funcionario getFuncionarioNome(String nome) {
        Funcionario retorno = null;
        try{
            ResultSet rs = getRS("SELECT * FROM funcionario WHERE nome = '"+nome+"'");
            retorno = new Funcionario();
            retorno.setNome(nome);
            retorno.setCodigo(rs.getString("cod"));
            retorno.setCpf(rs.getString("cpf"));
            retorno.setDataNascimento(rs.getString("dnasc"));
            retorno.setTelefone(getTelefone(retorno.getCodigo()));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno; 
    }

    @Override
    public Pessoa getPessoaNome(String nome) {
        Pessoa retorno = null;
        try{
            ResultSet rs = getRS("SELECT * FROM pessoa WHERE nome = '"+nome+"'");
            retorno = new Pessoa();
            retorno.setNome(nome);
            retorno.setCodigo(rs.getString("cod"));
            retorno.setCpf(rs.getString("cpf"));
            retorno.setDataNascimento(rs.getString("dnasc"));
            retorno.setTelefone(getTelefone(retorno.getCodigo()));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno; 
    }

    @Override
    public void setConsulta(Consulta novo) {
        try {
            if(novo.getCodigo() == null){
                String cliente = "'"+novo.getInicio()+"','"+novo.getTermino()+"','"+novo.getPaciente().getCodigo()+"','"+novo.getProfissional().getCodigo()+"'";
                String update = "INSERT INTO consulta(hinicio,hfim,codC,codF) VALUES("+cliente+")";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
            }else{
                String cliente = "hinicio = '"+novo.getInicio()+"',hfim = '"+novo.getTermino()+"',codC = '"+novo.getPaciente().getCodigo()+"',codF = '"+novo.getProfissional().getCodigo()+"'";
                String update = "UPDATE consulta SET "+cliente+" WHERE codconsulta = '"+novo.getCodigo()+"'";
                System.out.println(update);
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public Iterator<Consulta> getConsultaDia(String inicio, String fim) {
        List<Consulta> retorno = new ArrayList<Consulta>();
        try {
            List<String> codigos = new ArrayList<String>();
            
            ResultSet rs = getRS("SELECT codconsulta FROM consulta WHERE hinicio BETWEEN '"+inicio+"' AND '"+fim+"'");
            while(rs.next()){
                codigos.add(rs.getString("codconsulta"));
               // System.out.println(rs.getString("codconsulta"));
            }
            Iterator<String> it = codigos.iterator();
            while(it.hasNext()){
                Consulta novo = getConsulta(it.next());
                retorno.add(novo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(retorno.size());
        return retorno.iterator();
    }

    @Override
    public Iterator<Consulta> getConsultaEspecifiaca(String classe, String valor) {
        List<Consulta> retorno = new ArrayList<Consulta>();
        try {
            List<String> codigos = new ArrayList<String>();
            ResultSet rs = getRS("SELECT codconsulta FROM consulta WHERE "+classe+ "= '"+valor+"'");
            while(rs.next()){
                codigos.add(rs.getString("codconsulta"));
            }
            Iterator<String> it = codigos.iterator();
            while(it.hasNext()){
                Consulta novo = getConsulta(it.next());
                retorno.add(novo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return retorno.iterator();
    }
   
}
