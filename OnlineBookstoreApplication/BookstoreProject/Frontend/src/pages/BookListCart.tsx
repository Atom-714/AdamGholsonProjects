import { useEffect, useState } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import Alert from 'react-bootstrap/Alert';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

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

  const [showModal1, setShowModal1] = useState(false);
  const [showModal2, setShowModal2] = useState(false);
  const [showModal3, setShowModal3] = useState(false);
  const [showModal4, setShowModal4] = useState(false);

  const [selectedPayment, setSelectedPayment] = useState<number | null>(null);

  const handleCloseModal1 = async () => {
    try {
      setShowModal1(false);
      const res = await axios.post('/api/cart/next');
    } catch (err) {
      console.error(err);
      setError('Failed to change state.');
    }
  };
  const handleCloseModal2 = async () => {
    try {
      setShowModal2(false);
      const res = await axios.post('/api/cart/next');
    } catch (err) {
      console.error(err);
      setError('Failed to change state.');
    }
  };
  const handleCloseModal3 = async () => {
  try {
    if (selectedPayment === null) {
      alert("Please select a payment method first!");
      return;
    }
    await axios.post(`/api/cart/changeStrategy/${selectedPayment}`);
    setShowModal3(false);
    await axios.post('/api/cart/next');
    window.location.reload();
  } catch (err) {
    console.error(err);
    setError('Failed to process payment.');
  }
};
  const handleCloseModal4 = () => setShowModal4(false);
  const handleShowModal1 = () => setShowModal1(true);
  const handleShowModal2 = () => setShowModal2(true);
  const handleShowModal3 = () => setShowModal3(true);
  const handleShowModal4 = () => setShowModal4(true);

  const fetchBooks = async () => {
    try {
      setLoading(true);
      setError(null);
      const res = await axios.get<Book[]>('/api/book/cart');
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

  const handleRemoveFromCart = async (bookId: number) => {
  try {
    await axios.put(`/api/book/${bookId}/removeFromCart`);
    alert('Book removed from cart!');
    fetchBooks();
  } catch (err) {
    console.error(err);
    alert('Failed to remove book from cart.');
  }
};

  const handlePurchase = async () => {
  try {
    await axios.post(`/api/cart/startPurchase`);
    handleShowModal1();
    fetchBooks();
  } catch (err) {
    console.error(err);
    alert('Failed to Purchase.');
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
            <th style={{width: 150}}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.length === 0 ? (
            <tr>
              <td colSpan={5} className="text-center text-muted">
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
                <td>
                <Button variant="primary" size="sm" onClick={() => handleRemoveFromCart(b.id)}>
                  Remove From Cart
                </Button>
              </td>
              </tr>
            ))
          )}
        </tbody>
      </Table>
      {/* Modal 1 */}
      <Modal show={showModal1} onHide={handleCloseModal1}>
        <Modal.Header closeButton>
          <Modal.Title>Confermation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Are you sure about your purchase?</p>
          <Button variant="primary" onClick={() => {
            handleCloseModal1(); // Close the first modal
            handleShowModal2();   // Open the second modal
          }}>
            Yes
          </Button>
          <Button variant="primary" onClick={() => {
            handleCloseModal1(); // Close the first modal
          }}>
            No
          </Button>
        </Modal.Body>
      </Modal>

      {/* Modal 2 */}
      <Modal show={showModal2} onHide={handleCloseModal2}>
        <Modal.Header closeButton>
          <Modal.Title>Input Adress</Modal.Title>
        </Modal.Header>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Input Delivery Address</Form.Label>
          <Form.Control type="input" placeholder="address" name="address"/>
        </Form.Group>
        <Modal.Body>
          <Button variant="primary" onClick={() => {
            handleCloseModal2();
            handleShowModal3();
          }}>
            To Payment
          </Button>
        </Modal.Body>
      </Modal>

      {/* Modal 3 */}
      <Modal show={showModal3} onHide={handleCloseModal3}>
        <Modal.Header closeButton>
          <Modal.Title>Payment</Modal.Title>
        </Modal.Header>
        <Form.Group className="mb-3">
          <Form.Label>Payment Type:</Form.Label>
          <DropdownButton
            id="dropdown-basic-button"
            title={
              selectedPayment === null
                ? "Select Payment"
                : selectedPayment === 0
                ? "Credit Card"
                : selectedPayment === 1
                ? "PayPal"
                : "Gift Card"
            }
            onSelect={(eventKey) => {
              if (eventKey === "CreditCard") setSelectedPayment(0);
              if (eventKey === "PayPal") setSelectedPayment(1);
              if (eventKey === "GiftCard") setSelectedPayment(2);
            }}
          >
          <Dropdown.Item eventKey="CreditCard">Credit Card</Dropdown.Item>
          <Dropdown.Item eventKey="PayPal">PayPal</Dropdown.Item>
          <Dropdown.Item eventKey="GiftCard">Gift Card</Dropdown.Item>
          </DropdownButton>
        </Form.Group>
        <Modal.Body>
          <Button variant="primary" onClick={() => {
            handleCloseModal3();
            handleShowModal4();
          }}>
            To Payment
          </Button>
        </Modal.Body>
      </Modal>

      {/* Modal 4 */}
      <Modal show={showModal4} onHide={handleCloseModal4}>
        <Modal.Header closeButton>
          <Modal.Title>Complete</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Your purchase is complete</p>
          <Button variant="primary" onClick={() => {
            handleCloseModal4(); // Close the first modal
          }}>
            close
          </Button>
        </Modal.Body>
      </Modal>

      {/* Button to initially open Modal 1 */}
      <Button variant="primary" type="submit" onClick={() => handlePurchase()}>
        Checkout
      </Button>
    </div>
  );
}
