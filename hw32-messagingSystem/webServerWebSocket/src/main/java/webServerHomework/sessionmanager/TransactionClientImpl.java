package webServerHomework.sessionmanager;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Component
public class TransactionClientImpl implements TransactionClient {


    @Override
    @Transactional
    public <T> T doInTransaction(TransactionAction<T> action) {
        return action.get();
    }
}
