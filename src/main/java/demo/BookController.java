package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    /**
     * Get list of book
     * @return Books
     */
    @GetMapping
    public List<Book> getAllBookRecords() {
        return bookRepository.findAll();
    }

    /**
     * get bookId
     * @param bookId the bookId
     * @return book by Id
     */
    @GetMapping(value = "{bookId}")
    public Book getBookById(@PathVariable(value = "bookId") Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    /**
     * add book
     * @param bookRecord the bookRecord
     * @return save book
     */
    @PostMapping
    public Book createBookRecord(@RequestBody Book bookRecord) {
        return bookRepository.save(bookRecord);
    }

    /**
     * update the book
     * @param bookRecord the bookRecord
     * @return updated book
     * @throws ChangeSetPersister.NotFoundException
     */
    @PutMapping
    public Book updateBookRecord(@RequestBody Book bookRecord) throws ChangeSetPersister.NotFoundException {

        if(bookRecord == null || bookRecord.getBookId() == null) {
            throw new NullPointerException("Book record or Id must not be Null");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookRecord.getBookId());
        if(!optionalBook.isPresent()) {
            throw new NullPointerException("Book id" + bookRecord.getBookId() + "Does not the exist");
        }

        Book existingBookRecord = optionalBook.get();
        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setSummary(bookRecord.getSummary());
        existingBookRecord.setRating(bookRecord.getRating());
        return bookRepository.save(existingBookRecord);

    }

}
