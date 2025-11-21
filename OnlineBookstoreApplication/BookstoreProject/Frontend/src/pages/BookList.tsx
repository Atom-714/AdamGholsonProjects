import { useEffect, useState } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import Alert from 'react-bootstrap/Alert';

type Book = {
  id: number;
  title: string;
  author: string;
  price: string;
  available?: boolean;      
  createdByUsername?: string;  
};

export default function BookList() {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchBooks = async () => {
    try {
      setLoading(true);
      setError(null);
      const res = await axios.get<Book[]>('/api/book');
      setBooks(res.data ?? []);
    } catch (err) {
      console.error(err);
      setError('Failed to load books.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const handleAddToCart = async (bookId: number) => {
  try {
    await axios.put(`/api/book/${bookId}/addToCart`);
    alert('Book added to cart!');
    fetchBooks();
  } catch (err) {
    console.error(err);
    alert('Failed to add book to cart.');
  }
};

  if (loading) {
    return (
      <div className="d-flex align-items-center gap-2">
        <Spinner animation="border" size="sm" />
        <span>Loading books...</span>
      </div>
    );
  }

  if (error) {
    return (
      <Alert variant="danger" className="d-flex align-items-center justify-content-between">
        <span>{error}</span>
        <Button variant="outline-light" size="sm" onClick={fetchBooks}>
          Retry
        </Button>
      </Alert>
    );
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-2">
        <h5 className="m-0">Books</h5>
        <Button variant="secondary" size="sm" onClick={fetchBooks}>
          Refresh
        </Button>
      </div>

      <Table striped bordered hover responsive size="sm">
        <thead>
          <tr>
            <th style={{width: 80}}>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Price</th>
            <th style={{width: 120}}>Available</th>
            <th style={{width: 150}}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.length === 0 ? (
            <tr>
              <td colSpan={6} className="text-center text-muted">
                No books found.
              </td>
            </tr>
          ) : (
            books.map(b => (
              <tr key={b.id}>
                <td>{b.id}</td>
                <td>{b.title}</td>
                <td>{b.author}</td>
                <td>{b.price}</td>
                <td className={b.available === undefined ? '' : (b.available ? 'text-success' : 'text-danger')}>
                  {b.available === undefined ? '-' : (b.available ? 'Yes' : 'No')}
                </td>
                <td>
                  {b.available ? (
                  <Button
                    variant="primary"
                    size="sm"
                    onClick={() => handleAddToCart(b.id)}
                  >
                  Add to Cart
                  </Button>
                  ) : (
                   <span className="text-muted">Unavailable</span>
                  )}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </Table>
    </div>
  );
}
