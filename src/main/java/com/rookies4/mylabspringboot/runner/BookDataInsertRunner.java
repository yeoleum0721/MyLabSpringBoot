package com.rookies4.mylabspringboot.runner;

import com.rookies4.mylabspringboot.entity.Book;
import com.rookies4.mylabspringboot.entity.BookDetail;
import com.rookies4.mylabspringboot.entity.Publisher;
import com.rookies4.mylabspringboot.repository.BookRepository;
import com.rookies4.mylabspringboot.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class BookDataInsertRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    /**
     * 어플리케이션 시작 시 실행되는 메서드
     * Book과 BookDetail 샘플 데이터를 생성합니다.
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Starting Book-only data initialization...");

        // 기존 데이터가 있는지 확인 (Publisher 없는 Book이 있는지)
        long existingBookCount = bookRepository.count();

        // 이미 많은 데이터가 있다면 skip
        if (existingBookCount >= 15) {
            log.info("Sufficient book data already exists ({} books), skipping Book-only initialization", existingBookCount);
            return;
        }

        //Publisher 생성
        List<Publisher> publishers = createPublisher();

        // Book과 BookDetail 생성
        createBooksWithDetails(publishers);

        log.info("Book-only data initialization completed successfully");
    }

    private List<Publisher> createPublisher(){
        log.info("Creating Publishers ...");

        Publisher penguinRandomHouse = Publisher.builder()
                .name("Penguin Random House")
                .address("1745 Broadway, New York, NY 10019")
                .establishedDate(LocalDate.of(2013, 7, 1))
                .build();

        Publisher oReillyMedia = Publisher.builder()
                .name("O'Reilly Media")
                .address("1005 Gravenstein Highway North, Sebastopol, CA 95472")
                .establishedDate(LocalDate.of(1978, 1, 1))
                .build();

        Publisher prenticeHall = Publisher.builder()
                .name("Prentice Hall")
                .address("Old Tappan, NJ")
                .establishedDate(LocalDate.of(1913, 1, 1))
                .build();

        Publisher manningPublications = Publisher.builder()
                .name("Manning Publications")
                .address("20 Baldwin Road, PO Box 761, Shelter Island, NY 11964")
                .establishedDate(LocalDate.of(1993, 1, 1))
                .build();

        List<Publisher> publishers = publisherRepository.saveAll(
                List.of(penguinRandomHouse, oReillyMedia, prenticeHall, manningPublications)
        );
        log.info("Created {} publishers", publishers.size());
        return publishers;

    }

    /**
     * Book과 BookDetail 샘플 데이터를 생성합니다.
     * Publisher 정보는 null로 설정됩니다.
     */
    private void createBooksWithDetails(List<Publisher> publishers){
        log.info("Creating books ... ");

        Publisher penguinRandomHouse = publishers.get(0);
        Publisher oReillyMedia = publishers.get(1);
        Publisher prenticeHall = publishers.get(2);
        Publisher manningPublications = publishers.get(3);

        Book book1 = createBookWithDetail(
                "Effective Java", "Joshua Bloch", "978-0134685991", 38000,
                LocalDate.of(2018, 1, 6), oReillyMedia,
                "A comprehensive guide to the Java programming language and its core libraries",
                "English", 416, "Addison-Wesley Professional", "https://example.com/effective-java.jpg", "3rd Edition"
        );
        Book book2 = createBookWithDetail(
                "Java: The Complete Reference", "Herbert Schildt", "978-126044023", 450000,
                LocalDate.of(2017, 5, 26), oReillyMedia,
                "The definitive Java programming guide",
                "English", 1368, "McGraw-Hill Education", "https://example.com/java-complete.jpg", "11th Edition"
        );
        Book book3 = createBookWithDetail(
                "Clean Code", "Robert C. Martin", "978-0132350884", 42000,
                LocalDate.of(2008, 8, 1),prenticeHall,
                "A handbook of Agile software craftsmanship, covering practices for writing clean, readable, and maintainable code.",
                "English", 464, "Prentice Hall", "https://example.com/clean-code.jpg", "1st Edition"
        );
        Book book4 = createBookWithDetail(
                "The Lord of the Rings", "J.R.R. Tolkien", "978-0618053267", 25000,
                LocalDate.of(1954, 7, 29),prenticeHall,
                "A high-fantasy adventure novel about a hobbit's quest to destroy a powerful ring and save Middle-earth.",
                "English", 1178, "Allen & Unwin", "https://example.com/lord-of-the-rings.jpg", "1st Edition"
        );
        Book book5 = createBookWithDetail(
                "1984", "George Orwell", "978-0451524935", 15000,
                LocalDate.of(1949, 6, 8),manningPublications,
                "A dystopian social science fiction novel and cautionary tale about a totalitarian regime and state control.",
                "English", 328, "Secker & Warburg", "https://example.com/1984.jpg", "1st Edition"
        );
        Book book6 = createBookWithDetail(
                "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "978-0345391803", 12000,
                LocalDate.of(1979, 10, 12),manningPublications,
                "A science fiction comedy series that follows the adventures of an Englishman after the Earth is destroyed.",
                "English", 216, "Pan Books", "https://example.com/hitchhikers-guide.jpg", "1st Edition"
        );
        Book book7 = createBookWithDetail(
                "Atomic Habits", "James Clear", "978-1847941831", 13000,
                LocalDate.of(2017, 8, 24), penguinRandomHouse,
                "An Easy and Proven Way to Build Good Habits and Break Bad Ones ",
                "English", 268, "Random House UK Ltd", "https://example.com/Atomic-Habits.jpg", "4th Edition"
        );
        Book book8 = createBookWithDetail(
                "The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", 29700,
                LocalDate.of(2022, 2, 24), penguinRandomHouse,
                "A guide to the art and science of software development, focusing on practical tips and tricks for improving a programmer's daily work.",
                "English", 416, "Insight", "https://example.com/The-Pragmatic-Programmer.jpg", "2nd Edition"
        );
        List<Book> books = bookRepository.saveAll(
                List.of(book1,book2, book3, book4, book5, book6, book7, book8)
        );

        log.info("Created {} books with/without details", books.size());
    }

    /**
     * Book과 BookDetail을 함께 생성하는 헬퍼 메서드
     * 양방향 연관관계를 올바르게 설정합니다.
     *
     * @param title 도서 제목
     * @param author 저자
     * @param isbn ISBN
     * @param price 가격
     * @param publishDate 출간일
     * @param publishers 출판사
     * @param description 도서 설명
     * @param language 언어
     * @param pageCount 페이지 수
     * @param publisher 상세 정보의 출판사 (BookDetail의 publisher 필드)
     * @param coverImageUrl 표지 이미지 URL
     * @param edition 판본
     * @return 생성된 Book 엔티티 (BookDetail과 연관관계 설정됨)
     */
    private Book createBookWithDetail(String title, String author, String isbn, Integer price,
                                      LocalDate publishDate, Publisher publishers, String description, String language,
                                      Integer pageCount, String publisher, String coverImageUrl, String edition) {

        // BookDetail 생성
        BookDetail detail = BookDetail.builder()
                .description(description)
                .language(language)
                .pageCount(pageCount)
                .publisher(publisher) // 이것은 BookDetail의 publisher 필드 (String 타입)
                .coverImageUrl(coverImageUrl)
                .edition(edition)
                .build();

        // Book 생성 (Publisher 엔티티는 null)
        Book book = Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .price(price)
                .publishDate(publishDate)
                .publisher(publishers)
                .bookDetail(detail)
                .build();

        // 양방향 연관관계 설정
        detail.setBook(book);

        return book;
    }
}