package com.locadora.repository;

import com.locadora.conn.ConnectionFactory;
import com.locadora.domain.Cliente;
import com.locadora.domain.Locacao;
import com.locadora.domain.Status;
import com.locadora.domain.Veiculo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoRepositoryJdbc {

    public static Locacao salvar(Locacao locacao){
       String sql = "INSERT INTO `locadora_db`.`locacao` " +
               "(`cliente_id`, `veiculo_id`, `data_inicio`, `data_fim_prevista`, `status`) " +
               "VALUES (?, ?, ?, ?, 'EM_ANDAMENTO');";
       try(Connection conn = ConnectionFactory.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

           stmt.setLong(1,locacao.getCliente().getId());
           stmt.setLong(2,locacao.getVeiculo().getId());
           stmt.setObject(3,locacao.getDataInicio());
           stmt.setObject(4,locacao.getDataFimPrevista());
           stmt.executeUpdate();


           try (ResultSet rs = stmt.getGeneratedKeys()) {
               if (rs.next()) {
                   locacao.setId(rs.getLong(1));
               }
           }

           return locacao;
       } catch (SQLException e) {
           throw new RuntimeException("Erro ao salvar as informações da locaçao no banco de dados", e);
       }
    }

    public static void deletar(Locacao locacao){
        String sql = "DELETE FROM locadora_db.locacao WHERE `id` = ? AND status = 'PAGO';";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, locacao.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao informaçoes da locação, ID  nao encontrado ", e);
        }
    }


    public static void atualizar(Long id,Status status){
        String sql = "UPDATE `locadora_db`.`locacao` SET `status` = ? WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,status.name());
            stmt.setLong(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Locacao> buscarTodos() {
        String sql = "SELECT `id`, `cliente_id`, `veiculo_id`, `data_inicio`, `data_fim_prevista`, `data_devolucao`, `valor_total`, `status` " +
                "FROM locacao WHERE id > 0;";
        List<Locacao> locacoes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                Veiculo veiculo = new Veiculo();
                cliente.setId(rs.getLong("cliente_id"));
                veiculo.setId(rs.getLong("veiculo_id"));

                Locacao locacao = new Locacao(
                        rs.getLong("id"),
                        cliente,
                        veiculo,
                        rs.getObject("data_inicio", LocalDate.class),
                        rs.getObject("data_fim_prevista", LocalDate.class),
                        rs.getObject("data_devolucao", LocalDate.class),
                        rs.getDouble("valor_total"),
                        Status.valueOf(rs.getString("status"))
                );
                locacoes.add(locacao);
            }

            return locacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculos", e);
        }
    }




    public static Locacao buscarPorId(Long id) {
        String sql = "SELECT `id`, `cliente_id`, `veiculo_id`, `data_inicio`, `data_fim_prevista`, `data_devolucao`, `valor_total`, `status` " +
                "FROM locacao WHERE veiculo_id = ?;";

        Locacao locacao = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                Veiculo veiculo = new Veiculo();
                cliente.setId(rs.getLong("cliente_id"));
                veiculo.setId(rs.getLong("veiculo_id"));

                locacao = new Locacao(
                        rs.getLong("id"),
                        cliente,
                        veiculo,
                        rs.getObject("data_inicio", LocalDate.class),
                        rs.getObject("data_fim_prevista", LocalDate.class),
                        rs.getObject("data_devolucao", LocalDate.class),
                        rs.getDouble("valor_total"),
                        Status.valueOf(rs.getString("status"))
                );

            }
            return locacao;


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculos", e);
        }
    }








    public static Locacao devolucao(String placa){
        String sql = """
                    SELECT
                        c.nome,
                        v.placa,
                        l.data_inicio,
                        l.data_fim_prevista,
                        l.data_devolucao,
                        l.valor_total
                    FROM locacao l
                    INNER JOIN cliente c ON l.cliente_id = c.id
                    INNER JOIN veiculo v ON l.veiculo_id = v.id
                    WHERE v.placa = ?
                    """;

        Locacao locacao = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, placa);

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setNome(rs.getString("nome"));

                    Veiculo veiculo = new Veiculo();
                    veiculo.setPlaca(rs.getString("placa"));

                    Locacao locacao1 = new Locacao(
                            cliente,
                            veiculo,
                            rs.getObject("data_inicio", LocalDate.class),
                            rs.getObject("data_fim_prevista", LocalDate.class),
                            rs.getObject("data_devolucao", LocalDate.class),
                            rs.getDouble("valor_total")
                    );
                    locacao = locacao1;
                }
                return locacao;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void atualizarLocacao(Long id,LocalDate localdate,double valor ) {
        String sql = "UPDATE `locadora_db`.`locacao` SET `data_devolucao` = ?, `valor_total` = ? WHERE (`id` = ?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, localdate);
            stmt.setDouble(2, valor);
            stmt.setLong(3, id);


            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
