import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimuladorDeProcessos {
    public static void salvarTabelaProcessos(List<Processo> processos) {
        try (FileWriter writer = new FileWriter("tabela_processos.txt")) {
            for (Processo processo : processos) {
                writer.write(processo.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void simular() {
        List<Processo> processos = new ArrayList<>();
        processos.add(new Processo(0, 10000));
        processos.add(new Processo(1, 5000));
        processos.add(new Processo(2, 7000));
        processos.add(new Processo(3, 3000));
        processos.add(new Processo(4, 3000));
        processos.add(new Processo(5, 8000));
        processos.add(new Processo(6, 2000));
        processos.add(new Processo(7, 5000));
        processos.add(new Processo(8, 4000));
        processos.add(new Processo(9, 10000));

        int quantum = 1000;
        Random random = new Random();

        while (true) {  // Alteração: Loop só sai quando todos os processos estão finalizados
            boolean todosFinalizados = true;  // Variável para verificar se todos estão finalizados

            for (Processo processo : processos) {
                if (!processo.getEstado().equals("FINALIZADO")) {
                    todosFinalizados = false;  // Se pelo menos um processo não finalizou, continua o loop

                    if (processo.getEstado().equals("PRONTO")) {
                        processo.atualizarEstado("EXECUTANDO");
                        salvarTabelaProcessos(processos);
                        int ciclosRestantes = quantum;

                        while (ciclosRestantes > 0 && processo.getTempoProcessado() < processo.getTempoTotalExecucao()) {
                            if (random.nextDouble() < 0.01) {
                                processo.realizarOperacaoES();
                                salvarTabelaProcessos(processos);
                                break;
                            }

                            processo.incrementarTempoProcessado();
                            ciclosRestantes--;
                        }

                        if (processo.getTempoProcessado() >= processo.getTempoTotalExecucao()) {
                            processo.finalizarProcesso();
                            salvarTabelaProcessos(processos);
                        } else if (processo.getEstado().equals("EXECUTANDO") && ciclosRestantes == 0) {
                            processo.atualizarEstado("PRONTO");
                            salvarTabelaProcessos(processos);
                        }
                    } else if (processo.getEstado().equals("BLOQUEADO")) {
                        if (random.nextDouble() < 0.3) {
                            processo.atualizarEstado("PRONTO");
                            salvarTabelaProcessos(processos);
                        }
                    }
                }
            }

            // Se todos os processos foram finalizados, sai do loop
            if (todosFinalizados) break;
        }
    }

    public static void main(String[] args) {
        simular();
    }
}
