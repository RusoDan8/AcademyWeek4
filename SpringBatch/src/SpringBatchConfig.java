package com.javatechie.spring.batch.config;

import com.javatechie.spring.batch.entity.Book;
import com.javatechie.spring.batch.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private BookRepository bookRepository;

    // Reader para leer el archivo CSV de libros
    @Bean
    public FlatFileItemReader<Book> reader() {
        FlatFileItemReader<Book> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/Books.csv")); // Ruta del archivo CSV
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // Saltar la primera línea (encabezado)
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    // LineMapper para mapear las columnas del CSV a la entidad Book
    private LineMapper<Book> lineMapper() {
        DefaultLineMapper<Book> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "title", "author", "genre", "height", "publisher");

        BeanWrapperFieldSetMapper<Book> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Book.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    // Proceso para filtrar los libros (puedes personalizar esta lógica)
    @Bean
    public Step step1()
    {
        return stepBuilderFactory.get("csv-step")
                .<Book, Book>chunk(10)
                .reader(reader())
                .processor((ItemProcessor<Book, Book>) book -> {
                    // Procesar solo los libros cuyo género sea "Economics"
                    if (book.getGenre() != null && book.getGenre().equalsIgnoreCase("Economics")) {
                        return book; // Solo procesar libros de Economics
                    }
                    return null; // Ignorar si no es de Economics
                })
                .writer(writer()) // Usar el writer para guardar en la base de datos
                .taskExecutor(taskExecutor())
                .build();
    }

    // Writer para guardar los libros en la base de datos
    @Bean
    public RepositoryItemWriter<Book> writer() {
        RepositoryItemWriter<Book> writer = new RepositoryItemWriter<>();
        writer.setRepository(bookRepository);  // Configura el repositorio de JPA
        writer.setMethodName("save");  // El método que se llamará en el repositorio para guardar los datos
        return writer;
    }

    // Definir el Job que ejecutará el step
    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importBooks")
                .flow(step1())
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println("Job iniciado");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("Job terminado con estado: " + jobExecution.getStatus());
                    }
                })
                .build();
    }

    // TaskExecutor para manejar la concurrencia
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
