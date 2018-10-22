

//Singleton

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Agenda {
    
    private static Agenda objeto;
    private static IBancoDados db;
    private static LocalDate hoje;
    private static List<Consulta> consultaHoje;
    private static Consulta consultaAtual;
    private static boolean novosDados;
    
    public static Agenda getObjeto(){
        if(objeto == null)
            objeto = new Agenda();
        return objeto;
    }
    
    private Agenda(){
        db = new Persistencia();
        hoje = LocalDate.now();
        consultaHoje = new ArrayList<>();
        String data = hoje.getDayOfMonth() + "/" + hoje.getMonthValue() + "/" + hoje.getYear();
        Iterator<Consulta> it = db.getConsultaDia((data+" 00:00"), (data+" 23:59"));
        consultaAtual = getConsultaAtual(LocalTime.now());
        novosDados = false;
        while(it.hasNext())
            consultaHoje.add(it.next());
    }
    
    private static Consulta getConsultaAtual(LocalTime tempoAtual){
        Iterator<Consulta> it = consultaHoje.iterator();
        Consulta retorno = null;
        while(it.hasNext()){
            Consulta aux = it.next();
            //DD/MM/YYYY HH:MM
            int hora = Integer.parseInt(aux.getInicio().substring(11, 13)) - tempoAtual.getHour();
           if(hora == 0){
                int minuto = Integer.parseInt(aux.getInicio().substring(14, 16)) - tempoAtual.getMinute();
                if(minuto <= 0 && minuto >= -30){
                    retorno = aux;
                    break;
                }
            }
                
        }
        return retorno;
    }
    
    public static List<Consulta> getConsultasHoje(){
        return consultaHoje;
    }
    
    public static Consulta getConsultaNow(){
        return consultaAtual;
    }
    
    public static void atualizar(){
        consultaAtual = getConsultaAtual(LocalTime.now());
        if(novosDados){
            System.out.println("Atualizado");
            String data = hoje.getDayOfMonth() + "/" + hoje.getMonthValue() + "/" + hoje.getYear();
            Iterator<Consulta> it = db.getConsultaDia((data+" 00:00"), (data+" 23:59"));
            consultaHoje = new ArrayList<>();
            while(it.hasNext())
            consultaHoje.add(it.next());
        }
        novosDados = false;
    }
    
    public static Pessoa getPessoa(String codigo){
        Pessoa retorno = FabricaPessoa.getPessoa(codigo);
        return retorno;
    }
    
    public static Pessoa getPessoaNome(String nome){
        Pessoa retorno = FabricaPessoa.getPessoaNome(nome);
        return retorno;
    }
    
    public static Cliente getCliente(String codigo){
        return db.getCliente(codigo);
    }
    
    public static Cliente getClienteNome(String codigo){
        return db.getClienteNome(codigo);
    }
    
    public static Funcionario getFuncionario(String codigo){
        return db.getFuncionarioNome(codigo);
    }
    
    public static void setCliente(Cliente novo){
        novosDados = true;
        db.setCliente(novo);
    }
    public static void setFuncionario(Funcionario novo){
        novosDados = true;
        db.setFuncionario(novo);
    }
    public static void setConsulta(Consulta novo){
        novosDados = true;
        db.setConsulta(novo);
    }
    public static Consulta getConsulta(String codigo){
        return db.getConsulta(codigo);
    }
    
    public static Iterator<Consulta> getConsultaCliente(String valor){
        Iterator<Consulta> it = db.getConsultaEspecifiaca("codC", valor);
        return it;
    }
    
    public static Iterator<Consulta> getConsultaFuncionario(String valor){
        Iterator<Consulta> it = db.getConsultaEspecifiaca("codF", valor);
        return it;
    }
    

}
