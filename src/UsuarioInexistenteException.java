
class UsuarioInexistenteException extends RuntimeException {
        public String mensagem(){
            return "Este usuário não existe";
        }
    }