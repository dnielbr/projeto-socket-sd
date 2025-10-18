import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BancoRepositorio {

    private final List<Personagem> personagens;
    private final List<Habilidade> habilidades;

    public BancoRepositorio(){

        this.personagens = List.of(
                new Personagem("Neo", "Hacker talentoso e programador"),
                new Personagem("Morpheus", "Líder da tripulação da nave Nabucodonosor,"),
                new Personagem("Trinity", "Hacker habilidosa e combatente leal")
        );

        this.habilidades = List.of(
                new Habilidade("Manipulação da realidade da Matrix", "Personagens como Neo conseguem alterar a física e a lógica do mundo virtual"),
                new Habilidade("Artes marciais avançadas","Treinamentos virtuais permitem que os personagens dominem diversas técnicas de combate corpo a corpo instantaneamente."),
                new Habilidade("Hackeamento/entrada na Matrix", "Capacidade de conectar mentes à Matrix e acessar informações ou instalar habilidades")
        );
    }

    public List<Personagem> listarPersonagens() {
        return new ArrayList<>(personagens);
    }

    public List<Habilidade> listarHabilidades(){return new ArrayList<>(habilidades);}

}
