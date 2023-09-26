package org.folio.dcb.service;

import org.folio.dcb.domain.dto.TransactionStatus;
import org.folio.dcb.domain.dto.TransactionStatusResponse;
import org.folio.dcb.domain.entity.TransactionEntity;
import org.folio.dcb.domain.mapper.DcbTransactionMapper;
import org.folio.dcb.repository.TransactionRepository;
import org.folio.dcb.service.impl.LendingLibraryServiceImpl;
import org.folio.dcb.service.impl.TransactionsServiceImpl;
import org.folio.spring.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.folio.dcb.service.TransactionsService.DCB_TRANSACTION_WAS_NOT_FOUND_BY_ID;
import static org.folio.dcb.utils.EntityUtils.DCB_TRANSACTION_ID;
import static org.folio.dcb.utils.EntityUtils.createDcbTransaction;
import static org.folio.dcb.utils.EntityUtils.createTransactionResponse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @InjectMocks
  private TransactionsServiceImpl transactionsService;
  @Mock(name="lendingLibraryService")
  private LendingLibraryServiceImpl lendingLibraryService;
  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private DcbTransactionMapper dcbTransactionMapper;


  @Test
  void createCirculationRequestTest() {
    when(lendingLibraryService.createTransaction(any(), any()))
      .thenReturn(createTransactionResponse());
    transactionsService.createCirculationRequest(DCB_TRANSACTION_ID, createDcbTransaction());
    verify(lendingLibraryService).createTransaction(DCB_TRANSACTION_ID, createDcbTransaction());
  }

  @Test
  void shouldReturnAnyTransactionStatusById(){
    var transactionIdUnique = UUID.randomUUID().toString();
    when(transactionRepository.findById(transactionIdUnique))
      .thenReturn(Optional.ofNullable(TransactionEntity.builder().status(TransactionStatus.StatusEnum.CREATED).build()));
    when(dcbTransactionMapper.mapToDcbTransaction(any(TransactionEntity.class)))
      .thenReturn(createDcbTransaction());

    var trnInstance = transactionsService.getTransactionStatusById(transactionIdUnique);
    assertNotNull(trnInstance);
    assertEquals(trnInstance.getStatus(), TransactionStatusResponse.StatusEnum.CREATED);
  }

  @Test
  void getTransactionStatusByIdNotFoundExceptionTest(){
    var transactionIdUnique = UUID.randomUUID().toString();
    when(transactionRepository.findById(transactionIdUnique))
      .thenReturn(Optional.empty());

    Throwable exception = assertThrows(
      NotFoundException.class, () -> transactionsService.getTransactionStatusById(transactionIdUnique)
    );

    Assertions.assertEquals(DCB_TRANSACTION_WAS_NOT_FOUND_BY_ID + transactionIdUnique, exception.getMessage());
  }
}
