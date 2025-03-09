// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
public class Book {
    public string Title { get; private set; }
    public Book(string title) {
        Title = title;
    }
}

// The aggregate class: a bookshelf that holds multiple books.
// This class implements IEnumerable<Book> so that it can be iterated over with foreach.
public class BookShelf : IEnumerable<Book> {
    private Book[] books;
    private int count;

    public BookShelf(int capacity) {
        books = new Book[capacity];
        count = 0;
    }

    // Method to add a book to the shelf.
    public void AddBook(Book book) {
        if (count < books.Length) {
            books[count] = book;
            count++;
        } else {
            throw new Exception("The bookshelf is full!");
        }
    }

    // The iterator: uses yield return to iterate over the collection.
    public IEnumerator<Book> GetEnumerator() {
        for (int i = 0; i < count; i++) {
            yield return books[i];
        }
    }

    // Explicit non-generic implementation for backwards compatibility.
    IEnumerator IEnumerable.GetEnumerator() {
        return GetEnumerator();
    }
}

// The client code that uses the iterator to traverse the BookShelf.
public class Program {
    public static void Main(string[] args) {
        // Create a new bookshelf with a capacity of 5.
        BookShelf shelf = new BookShelf(5);
        shelf.AddBook(new Book("1984"));
        shelf.AddBook(new Book("Brave New World"));
        shelf.AddBook(new Book("Fahrenheit 451"));

        // Using foreach, which under the hood uses the iterator we defined.
        foreach (Book book in shelf) {
            Console.WriteLine(book.Title);
        }
    }
}