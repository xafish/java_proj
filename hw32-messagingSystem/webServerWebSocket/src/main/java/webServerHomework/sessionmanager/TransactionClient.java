package webServerHomework.sessionmanager;

public interface TransactionClient {

    <T> T doInTransaction(TransactionAction<T> action);
}
