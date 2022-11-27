import java.util.Scanner;
import java.util.Arrays;
import java.util.Objects;

public class ProjetoFinal {
	static Perfil[] redeSocial = new Perfil[1];
    static Perfil novoPerfil;
    static Perfil usuarioLogado;

	public static void main(String[] args) {
		yoda();
        System.out.println("Bem vindo(a) à Rede Social");
        menuInicial();
	}
	
	static void menuInicial(){
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("Digite C para cadastrar um novo usuário.");
        System.out.println("Digite E para entrar em sua conta.");
        System.out.println("Digite F para fechar o programa.");

        String selecaoMenu = sc.nextLine();
        selecaoMenu = selecaoMenu.toUpperCase();

        switch (selecaoMenu) {
            case "C" -> cadastro();
            case "E" -> {
                try{
                    login();
                } catch (UsuarioInexistenteException e){
                    System.out.println(e.mensagem());
                    menuInicial();
                } catch (SenhaIncorretaException e){
                    System.out.println(e.mensagem());
                    menuInicial();
                }
            }
            case "F" -> System.out.println("Até mais!");
            default -> {
                System.out.println("Opção inválida.");
                System.out.println("");
                menuInicial();
            }
        }
        sc.close();
    }
    static void cadastro(){
        Scanner sc = new Scanner(System.in);
        boolean perfilExiste = false;
        boolean nome = false;
        boolean login = false;
        boolean senha = false;
        novoPerfil = new Perfil();

        System.out.println("Insira seu nome:");
        novoPerfil.nome = sc.nextLine();
        if(novoPerfil.nome.length() != 0){
            nome = true;
        }
        System.out.println("Digite um nome de usuário para fazer login:");
        novoPerfil.login = sc.nextLine();
        if(novoPerfil.login.length() != 0){
            login = true;
        }
        System.out.println("Crie uma senha:");
        novoPerfil.senha = sc.nextLine();
        if(novoPerfil.senha.length() != 0){
            senha = true;
        }

        if(!nome){
            System.out.println("Nome não pode ser vazio");
        }
        if(!login){
            System.out.println("Login não pode ser vazio");
        }
        if(!senha){
            System.out.println("Senha não pode ser vazio");
        }

        if(novoPerfil.nome.length() != 0 && novoPerfil.login.length() != 0 && novoPerfil.senha.length() != 0){
            for (Perfil perfis: redeSocial) {
                if (Objects.equals(perfis.login, novoPerfil.login)) {
                    perfilExiste = true;
                    break;
                }
            }
            if(perfilExiste){
                System.out.println("Este nome de usuário já existe.");
            } else{
                novoPerfil.timeline = new Post[0];
                redeSocial = Arrays.copyOf(redeSocial, redeSocial.length+1);
                redeSocial[redeSocial.length-1] = novoPerfil;
                System.out.println("Conta criada com sucesso!");
            }
            System.out.println("");
        }

        menuInicial();
        sc.close();
    }
    static void login() throws UsuarioInexistenteException, SenhaIncorretaException{
        Scanner sc = new Scanner(System.in);
        boolean usuarioExiste = false;
        System.out.println("Insira seu usuário de login");
        String usuario = sc.next();
        for (Perfil perfil: redeSocial) {
            if (perfil.login.equals(usuario)) {
                usuarioExiste = true;
                usuarioLogado = perfil;
                break;
            }
        }
        if(!usuarioExiste){
            throw new UsuarioInexistenteException();
        }

            System.out.println(usuarioLogado.nome +", digite sua senha");

            String senha = sc.next();
            if(usuarioLogado.senha.equals(senha)){
                System.out.println("");
                System.out.println("Bem vindo(a) " + usuarioLogado.nome);
                perfilLogado();
            } else {
                throw new SenhaIncorretaException();
            }

        sc.close();
    }

    static void perfilLogado(){
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("Digite P para postar algo novo");
        System.out.println("Digite T para visualizar sua timeline");
        System.out.println("Digite S para sair");

        String opcao = sc.next();
        opcao = opcao.toUpperCase();

        switch (opcao) {
            case "P" -> postar();
            case "T" -> timeline();
            case "S" -> {
                System.out.println("Até mais, " + usuarioLogado.nome + "!");
                menuInicial();
            }
            default -> {
                System.out.println("Opção Inválida");
                System.out.println("");
                perfilLogado();
            }
        }
        sc.close();
    }

    static void postar(){
        Scanner sc = new Scanner(System.in);
        Post novoPost = new Post();
        boolean dataCorreta = false;
        boolean horaCorreta = false;
        boolean conteudoCorreto = false;
        String data;
        String hora;
        String conteudo;

        System.out.println("Insira a data no formato DD/MM/AA");
        data = sc.nextLine();
        if(data.length() != 0) {
            dataCorreta = true;
        }

        System.out.println("Insira a hora no formato HH:MM");
        hora = sc.nextLine();
        if(hora.length() != 0){
            horaCorreta = true;
        }

        System.out.println("Insira o conteúdo do seu post");
        conteudo = sc.nextLine();
        if(conteudo.length() != 0){
            conteudoCorreto = true;
        }

        if(dataCorreta && horaCorreta && conteudoCorreto){
            novoPost.data = data;
            novoPost.hora = hora;
            novoPost.conteudo = conteudo;
            for (Perfil perfil: redeSocial) {
                if(Objects.equals(perfil.login, usuarioLogado.login)){
                    perfil.timeline = Arrays.copyOf(perfil.timeline, perfil.timeline.length+1);
                    perfil.timeline[perfil.timeline.length-1] = novoPost;
                }
            }
        }
        if(!dataCorreta){
            System.out.println("A data do seu post está incorreta");
        }
        if(!horaCorreta){
            System.out.println("A hora do seu post está incorreta");
        }
        if(!conteudoCorreto){
            System.out.println("O conteúdo do seu post está incorreto");
        }
        perfilLogado();
        sc.close();
    }
    static void timeline(){
        System.out.println("");
        for (Perfil perfis: redeSocial) {
            if(Objects.equals(perfis.login, usuarioLogado.login)){
                for (Post posts: perfis.timeline) {
                    System.out.println(posts.data);
                    System.out.println(posts.hora);
                    System.out.println(posts.conteudo);
                    System.out.println("");
                }
            }
        }
        if(usuarioLogado.timeline.length == 0){
            System.out.println("Você ainda não postou nada.");
        }
        perfilLogado();
    }

    static void yoda(){
        novoPerfil = new Perfil();
        novoPerfil.nome = "Yoda";
        novoPerfil.login = "mestre";
        novoPerfil.senha = "jediOrder";
        Post post1 = new Post();
        post1.data = "02/10/80";
        post1.hora = "20:42";
        post1.conteudo = "Do or do not. There is no try.";
        Post post2 = new Post();
        post2.data = "17/08/82";
        post2.hora = "19:57";
        post2.conteudo = "Train yourself to let go of everything you fear to lose.";
        Post post3 = new Post();
        post3.data = "22/10/87";
        post3.hora = "19:52";
        post3.conteudo = "Named must be your fear before banish it you can.";
        Post post4 = new Post();
        post4.data = "13/05/88";
        post4.hora = "08:12";
        post4.conteudo = "Fear is the path to the dark side.";
        Post post5 = new Post();
        post5.data = "28/10/89";
        post5.hora = "14:34";
        post5.conteudo = "Pass on what you have learned.";
        novoPerfil.timeline = new Post[]{post1, post2, post3, post4, post5};

        redeSocial[0] = novoPerfil;
    }
}