package com.example.demo;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Storagecontract extends Contract {
    private static final String BINARY = "6080604052600160005534801561001557600080fd5b50610119806100256000396000f300608060405260043610605c5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312065fe08114605e57806331b6bd06146082578063559c9c4a1460945780639d0f84da1460a9575b005b348015606957600080fd5b50607060af565b60408051918252519081900360200190f35b348015608d57600080fd5b50607060b4565b348015609f57600080fd5b50605c60043560ba565b605c60bf565b303190565b60005490565b600055565b60405130903480156108fc02916000818181858888f1935050505015801560ea573d6000803e3d6000fd5b505600a165627a7a72305820bb04c6538332024600154d46a8a5844b5f71e586c06a5b5ba112766e034968860029";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETVAL = "getval";

    public static final String FUNC_SETVAL = "setval";

    public static final String FUNC_ADDMONEY = "addmoney";

    protected Storagecontract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Storagecontract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getval() {
        final Function function = new Function(FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setval(BigInteger n) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(n)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addmoney(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ADDMONEY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static RemoteCall<Storagecontract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storagecontract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Storagecontract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storagecontract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Storagecontract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storagecontract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Storagecontract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storagecontract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
