package com.example.bank;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.account.AddAccountDto;
import com.example.bank.dto.account.ChangeAccountDto;
import com.example.bank.dto.account.ClientInAccountDto;
import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.dto.agreement.ChangeAgreementDto;
import com.example.bank.dto.client.ChangeClientDto;
import com.example.bank.dto.client.ClientDto;
import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.dto.product.ChangeProductDto;
import com.example.bank.dto.product.ProductDto;
import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.*;
import com.example.bank.entity.enums.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;

public class TestUtils {

    public static String readStringFromResource(String resourcePath) {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(MessageFormat.format("classpath:{0}", resourcePath));

        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Account createAccount(Long id, Client client) {

        Account account = new Account();
        account.setId(id);
        account.setName("MyAccount");
        account.setType(AccountType.CURRENT);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(50);
        account.setCurrencyCode(CurrencyCode.USD);
        account.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        account.setUpdatedAt(null);
        account.setClient(client);
        return account;

    }

    public static Client createClient(Long id, Manager manager) {

        Client client = new Client();
        client.setId(id);
        client.setStatus(ClientStatus.ACTIVE);
        client.setTaxCode("5658380715");
        client.setFirstName("Mark");
        client.setLastName("Smith");
        client.setEmail("mark@gmail.com");
        client.setAddress("18485 Ronald Tunnel, London");
        client.setPhone("19990006677");
        client.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        client.setUpdatedAt(null);
        client.setManager(manager);
        return client;

    }

    public static Manager createManager(Long id) {

        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setStatus(ManagerStatus.valueOf("WORKING"));
        manager.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        manager.setUpdatedAt(null);
        return manager;
    }

    public static Product createProduct(Long id, Long managerId) {

        Product product = new Product();

        product.setId(id);
        product.setName("Credit");
        product.setProductLimit(500000);
        product.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        product.setUpdatedAt(null);
        product.setInterestRate(0.001);
        product.setCurrencyCode(CurrencyCode.EUR);
        product.setStatus(ProductStatus.ACTIVE);
        product.setManager(createManager(managerId));

        return product;
    }

    public static Agreement createAgreement (Long id) {

        Agreement agreement = new Agreement();

        agreement.setId(id);
        agreement.setAccount(createAccount(1L, createClient(1L, createManager(1L))));
        agreement.setProduct(createProduct(1L, 1L));
        agreement.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        agreement.setUpdatedAt(null);
        agreement.setInterestRate(BigDecimal.valueOf(0.001));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setSum(BigDecimal.valueOf(55000));

        return agreement;
    }

    public static Transaction createTransaction (Long id) {

        Transaction transaction = new Transaction();

        transaction.setId(id);
        transaction.setCreditAccount(createAccount(1L, createClient(1L, createManager(1L))));
        transaction.setDebitAccount(createAccount(2L, createClient(2L, createManager(2L))));
        transaction.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        transaction.setAmount(10000);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setDescription("Transfer money");

        return transaction;
    }

    public static TransactionDto createTransactionDto (Long id) {

        TransactionDto dto = new TransactionDto();

        dto.setCreditAccountId(1L);
        dto.setDebitAccountId(2L);
        dto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        dto.setAmount(10000);
        dto.setType(TransactionType.TRANSFER);
        dto.setDescription("Transfer money");

        return dto;
    }

    public static AgreementDto createAgreementDto (Long id) {

        AgreementDto agreementDto = new AgreementDto();

        agreementDto.setAccountId(1L);
        agreementDto.setProductId(1L);
        agreementDto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        agreementDto.setUpdatedAt(null);
        agreementDto.setInterestRate(BigDecimal.valueOf(0.001));
        agreementDto.setStatus(AgreementStatus.ACTIVE);
        agreementDto.setSum(BigDecimal.valueOf(55000));

        return agreementDto;
    }

    public static ProductDto createProductDto (Long id) {

        ProductDto productDto = new ProductDto();

        productDto.setName("Credit");
        productDto.setProductLimit(500000);
        productDto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        productDto.setUpdatedAt(null);
        productDto.setInterestRate(0.001);
        productDto.setCurrencyCode(CurrencyCode.EUR);
        productDto.setStatus(ProductStatus.ACTIVE);

        return productDto;
    }

    public static ManagerDto createManagerDto (Long id) {

        ManagerDto managerDto = new ManagerDto();

        managerDto.setFirstName("John");
        managerDto.setLastName("Doe");
        managerDto.setStatus(ManagerStatus.valueOf("WORKING"));

        return managerDto;
    }

    public static AccountDto createAccountDto (Long id, ClientInAccountDto clientDto) {

        AccountDto accountDto = new AccountDto();
        accountDto.setName("MyAccount");
        accountDto.setType(AccountType.CURRENT);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setBalance(50);
        accountDto.setCurrencyCode(CurrencyCode.USD);
        accountDto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        accountDto.setUpdatedAt(null);
        accountDto.setClient(clientDto);

        return accountDto;
    }

    public static AddAccountDto getAddAccountDto (int clientId) {

        AddAccountDto accountDto = new AddAccountDto();
        accountDto.setName("MyAccount");
        accountDto.setType(AccountType.CURRENT);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setBalance(50);
        accountDto.setCurrencyCode(CurrencyCode.USD);
        accountDto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        accountDto.setClientId(clientId);

        return accountDto;
    }
    public static AccountDto getChangedAccountDto (Long id, ChangeAccountDto changeAccountDto) {

        AccountDto accountDto = createAccountDto(id, createClientInAccountDto(1L));
        accountDto.setName(changeAccountDto.getName());
        accountDto.setType(changeAccountDto.getType());
        accountDto.setStatus(changeAccountDto.getStatus());
        accountDto.setBalance(changeAccountDto.getBalance());
        accountDto.setCurrencyCode(changeAccountDto.getCurrencyCode());

        return accountDto;
    }

    public static AgreementDto getChangedAgreementDto (Long id, ChangeAgreementDto changeAgreementDto) {

        AgreementDto agreementDto = createAgreementDto(id);
        agreementDto.setStatus(changeAgreementDto.getStatus());
        agreementDto.setSum(changeAgreementDto.getSum());
        agreementDto.setInterestRate(changeAgreementDto.getInterestRate());

        return agreementDto;
    }

    public static ProductDto getChangedProductDto (Long id, ChangeProductDto changeProductDto) {

        ProductDto productDto = createProductDto(id);
        productDto.setName(changeProductDto.getName());
        productDto.setProductLimit(changeProductDto.getProductLimit());
        productDto.setStatus(changeProductDto.getStatus());
        productDto.setInterestRate(changeProductDto.getInterestRate());
        productDto.setCurrencyCode(changeProductDto.getCurrencyCode());

        return productDto;
    }

    public static ClientInAccountDto createClientInAccountDto (Long id) {

        ClientInAccountDto clientDto = new ClientInAccountDto();

        clientDto.setFirstName("Mark");
        clientDto.setLastName("Smith");
        clientDto.setEmail("mark@gmail.com");
        clientDto.setPhone("19990006677");

        return clientDto;
    }

    public static ClientDto createClientDto (Long id, Long managerId) {

        ClientDto clientDto= new ClientDto(
                "Mark",
                "Smith",
                "5658380715",
                ClientStatus.ACTIVE,
                "mark@gmail.com",
                "19990006677",
                "18485 Ronald Tunnel, London",
                Timestamp.valueOf("2024-02-01 00:00:00"),
                null,
                1L
        );
        return clientDto;
    }

    public static ClientDto getChangedClientDto (Long id, ChangeClientDto dto) {

        ClientDto clientDto = createClientDto(1L, 1L);
        clientDto.setFirstName(dto.getFirstName());
        clientDto.setLastName(dto.getLastName());
        clientDto.setTaxCode(dto.getTaxCode());
        clientDto.setEmail(dto.getEmail());
        clientDto.setAddress(dto.getAddress());
        clientDto.setPhone(dto.getPhone());
        clientDto.setStatus(dto.getStatus());

        return clientDto;
    }
}
