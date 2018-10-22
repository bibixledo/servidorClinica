

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjetoRemoto implements IObjetoRemoto{

    protected ObjetoRemoto() throws RemoteException{
        super();
    }
    
    @Override
    public List<Consulta> getConsultaHoje() throws RemoteException {
        List<Consulta> retorno = Agenda.getObjeto().getConsultasHoje();
        System.out.println(retorno.size());
        return retorno;
    }

    @Override
    public List<Consulta> getConsulta(int tipo, String valor) throws RemoteException {
        //tipo 0 = cliente
        //tipo 1 = funcionario
        Iterator<Consulta> it = null;
        List<Consulta> retorno = new ArrayList<>();
        if(tipo == 0){
            it = Agenda.getObjeto().getConsultaCliente(valor);
        }else{
            it = Agenda.getObjeto().getConsultaFuncionario(valor);
        }
        while(it.hasNext())
            retorno.add(it.next());
        return retorno;
    }

    @Override
    public Consulta getConsultaAtual() throws RemoteException{
        return Agenda.getObjeto().getConsultaNow();
    }

    @Override
    public void setCliente(Cliente novo) throws RemoteException {
        Agenda.getObjeto().setCliente(novo);
    }

    @Override
    public void setFuncionario(Funcionario novo) throws RemoteException {
        Agenda.getObjeto().setFuncionario(novo);
    }

    @Override
    public void setConsulta(Consulta novo) throws RemoteException {
        Agenda.getObjeto().setConsulta(novo);
    }

    @Override
    public Pessoa getPessoa(int busca, String valor) throws RemoteException {
        Pessoa retorno = null;
        if(busca == 0){
            retorno = Agenda.getObjeto().getPessoa(valor);
        }else{
            retorno = Agenda.getObjeto().getPessoaNome(valor);
        }
        return retorno;
    }

    @Override
    public Cliente getCliente(String valor) throws RemoteException {
        Cliente r = null;
        r = Agenda.getObjeto().getClienteNome(valor);
        return r;
    }
    
    public Funcionario getFuncionario(String valor )throws RemoteException {
        Funcionario r = null;
        r = Agenda.getObjeto().getFuncionario(valor);
        return r;
    }

    @Override
    public List<Consulta> getConsultaCliente(String codigo) throws RemoteException {
        Iterator<Consulta> it = Agenda.getObjeto().getConsultaCliente(codigo);
        List<Consulta> r = new ArrayList<>();
        while(it.hasNext())
            r.add(it.next());
        return r;
    }

    @Override
    public List<Consulta> getConsultaFuncionario(String codigo) throws RemoteException {
Iterator<Consulta> it = Agenda.getObjeto().getConsultaFuncionario(codigo);
        List<Consulta> r = new ArrayList<>();
        while(it.hasNext())
            r.add(it.next());
        return r;    }
    
}
