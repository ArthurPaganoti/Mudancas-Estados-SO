class Processo {
    private int pid;
    private int tempoTotalExecucao;
    private int tempoProcessado = 0;
    private int contadorProcessamento = 1;
    private String estado = "PRONTO";
    private int numeroES = 0;
    private int numeroCPU = 0;

    public Processo(int pid, int tempoTotalExecucao) {
        this.pid = pid;
        this.tempoTotalExecucao = tempoTotalExecucao;
    }

    @Override
    public String toString() {
        return String.format("PID: %02d -> Estado: %-10s -> TP: %05d -> CP: %05d -> NES: %02d -> N_CPU: %02d",
                pid, estado, tempoProcessado, contadorProcessamento, numeroES, numeroCPU);
    }

    public void atualizarEstado(String novoEstado) {
        System.out.printf("PID %02d: %s -> %s | TP: %05d, CP: %05d, NES: %02d, N_CPU: %02d%n",
                pid, estado, novoEstado, tempoProcessado, contadorProcessamento, numeroES, numeroCPU);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        estado = novoEstado;
        if (novoEstado.equals("EXECUTANDO")) {
            numeroCPU++;
        }
    }

    public void realizarOperacaoES() {
        numeroES++;
        atualizarEstado("BLOQUEADO");
    }

    public int getPid() {
        return pid;
    }

    public int getTempoProcessado() {
        return tempoProcessado;
    }

    public void incrementarTempoProcessado() {
        tempoProcessado++;
        contadorProcessamento = tempoProcessado + 1;
        if (tempoProcessado >= tempoTotalExecucao) {
            finalizarProcesso();
        }
    }

    public int getTempoTotalExecucao() {
        return tempoTotalExecucao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void finalizarProcesso() {
        estado = "FINALIZADO";
        System.out.printf("PID %02d: Processo finalizado.%n", pid);
    }
}