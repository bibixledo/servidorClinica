

import java.util.Iterator;
import java.util.List;

public interface IBancoDados {
    public Iterator<Cliente> getListaCliente();
    public String[] getTelefone(String codigo);
    public Cliente getCliente(String codigo);
    public Funcionario getFuncionario(String codigo);
    public Cliente getClienteNome(String nome);
    public Funcionario getFuncionarioNome(String nome);
    public Pessoa getPessoaNome(String nome);
    public void setCliente(Cliente novo);
    public void setFuncionario(Funcionario novo);
    public void setConsulta(Consulta novo);
    public boolean hasCliente(String codigo);
    public boolean hasFuncionario(String codigo);
    public Consulta getConsulta(String codigo);
    public Iterator<Consulta> getListaConsulta();
    public Iterator<Consulta> getConsultaDia(String inicio,String fim);
    public Iterator<Consulta> getConsultaEspecifiaca(String classe,String valor);
}
