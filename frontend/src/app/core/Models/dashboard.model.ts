

export interface Dashboard {
    totalUsuarios: number;
    totalDoacoes: number;
    totalDoacoesRealizadas: number;
    doacoesAprovadas: number;
    doacoesReprovadas: number;
    doacoesReparo: number;
    doacoesPorMes : [
        {
            mes: string;
            total: number;
        }
    ]
    doacoesPorEquipamento: [
        {
            equipamento: string;
            total: number;
        }
    ]
}