package med.voll.api.model.adress;

public record CompleteAdressRec(
        String logradouro,
        String bairro,
        String cep,
        String cidade,
        String uf,
        String complemento,
        String numero
){}