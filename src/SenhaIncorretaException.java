class SenhaIncorretaException extends RuntimeException {
        public String mensagem(){
            return "A senha está incorreta";
        }
    }