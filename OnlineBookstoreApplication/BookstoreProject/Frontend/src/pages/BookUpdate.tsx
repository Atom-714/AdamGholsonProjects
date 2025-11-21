import { useState } from 'react';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Alert from 'react-bootstrap/Alert';

export default function BookUpdate() {
  const [bookId, setBookId] = useState('');
  const [book, setBook] = useState({
    title: '',
    author: '',
    price: '',
    available: true
  });
  const [message, setMessage] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axios.put(`/api/book/${bookId}`, book);
      setMessage('Book updated successfully!');
      setBook({ title: '', author: '', price: '', available: true });
      setBookId('');
    } catch (err) {
      console.error(err);
      setMessage('Failed to update book.');
    }
  };

  return (
    <div className="p-3">
      <h4>Update a Book</h4>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Book ID</Form.Label>
          <Form.Control
            type="number"
            name="id"
            value={bookId}
            placeholder="Enter Book ID to update"
            onChange={(e) => setBookId(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            name="title"
            value={book.title}
            placeholder="New Title"
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Author</Form.Label>
          <Form.Control
            type="text"
            name="author"
            value={book.author}
            placeholder="New Author"
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="text"
            name="price"
            value={book.price}
            placeholder="New Price"
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Check
            type="checkbox"
            name="available"
            label="Available"
            checked={book.available}
            onChange={(e) => setBook({ ...book, available: e.target.checked })}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Update Book
        </Button>
      </Form>
    </div>
  );
}