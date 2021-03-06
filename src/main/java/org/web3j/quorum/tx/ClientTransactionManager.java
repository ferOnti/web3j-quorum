package org.web3j.quorum.tx;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.quorum.methods.request.PrivateTransaction;
import org.web3j.tx.TransactionManager;

/**
 * TransactionManager implementation for using a Quorum node to transact.
 */
public class ClientTransactionManager implements TransactionManager {

    private final Web3j web3j;
    private final String fromAddress;
    private List<String> privateFor;

    public ClientTransactionManager(
            Web3j web3j, String fromAddress, List<String> privateFor) {
        this.web3j = web3j;
        this.fromAddress = fromAddress;
        this.privateFor = privateFor;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }

    @Override
    public EthSendTransaction executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value)
            throws ExecutionException, InterruptedException, TransactionTimeoutException {

        PrivateTransaction transaction = new PrivateTransaction(
                fromAddress, null, gasLimit, to, value, data, privateFor);

        return web3j.ethSendTransaction(transaction)
                .sendAsync().get();
    }
}
