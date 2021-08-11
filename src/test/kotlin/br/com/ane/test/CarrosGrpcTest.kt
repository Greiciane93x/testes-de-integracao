package br.com.ane.test
import br.com.ane.test.carros.Carro
import br.com.ane.test.carros.CarroRepository
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION,
    transactional = false
)
class CarrosGrpcTest {

    @Inject
    lateinit var repository: CarroRepository

    @BeforeEach
    fun setup(){
        repository.deleteAll()
    }

    @AfterEach
    fun cleanUp(){
        repository.deleteAll()
    }


    @Test
    fun `deve inserir um novo carro`() {
        repository.save(Carro(modelo = "Gol", placa = "HPF-3319"))
        Assertions.assertEquals(1, repository.count())
    }

    @Test
    fun `deve deletar um carro no banco de dados`(){
        var carro = Carro(modelo = "Hyundai Creta", placa = "RH8-2020")
        repository.delete(carro)
        Assertions.assertEquals(0, repository.count())
    }

    @Test
    fun `deve encontrar carro por placa`(){
        repository.deleteAll()
        repository.save(Carro(modelo = "Hyundai Tucson", placa = "XUR-2021"))

        val encontrado = repository.existsByPlaca("XUR-2021")

        Assertions.assertTrue(encontrado)

    }
    @Test
    fun `deve listar carros`(){
        repository.deleteAll()
        repository.save(Carro(modelo = "Hyundai Tucson", placa = "HHF-2021"))
        repository.save(Carro(modelo = "Fusca", placa = "LSV-2021"))
        repository.save(Carro(modelo = "Cobalt", placa = "KKS-2021"))

        Assertions.assertEquals(3, repository.count())
    }

    @Test
    fun `deve salvar carro`(){
        repository.deleteAll()
        repository.save(Carro(modelo = "Hyundai Creta", placa = "KKX-2021"))
        Assertions.assertEquals(1, repository.count())
    }

}
