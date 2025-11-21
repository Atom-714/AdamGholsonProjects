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
  price: number;
};

type Order = {
  orderId: number;
  totalPrice: number;
  books: Book[];
};

export default function UserPurchases() {
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchOrders = async () => {
    try {
      setLoading(true);
      setError(null);
      const res = await axios.get<Order[]>('api/cart/user/orders');
      setOrders(res.data ?? []);
    } catch (err) {
      console.error(err);
      setError('Failed to load purchase history.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  if (loading) {
    return (
      <div className="d-flex align-items-center gap-2">
        <Spinner animation="border" size="sm" />
        <span>Loading purchases...</span>
      </div>
    );
  }

  if (error) {
    return (
      <Alert variant="danger" className="d-flex justify-content-between">
        <span>{error}</span>
        <Button size="sm" onClick={fetchOrders}>Retry</Button>
      </Alert>
    );
  }

  return (
    <div>
      <div className="d-flex justify-content-between mb-2">
        <h5>Your Purchases</h5>
        <Button variant="secondary" size="sm" onClick={fetchOrders}>
          Refresh
        </Button>
      </div>

      {orders.length === 0 ? (
        <Alert variant="info">You have no past orders.</Alert>
      ) : (
        orders.map(order => (
          <div key={order.orderId} className="mb-4">
            <h6>Order #{order.orderId}</h6>
            <strong>Total: ${order.totalPrice.toFixed(2)}</strong>

            <Table striped bordered hover responsive size="sm" className="mt-2">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Title</th>
                  <th>Author</th>
                  <th>Price</th>
                </tr>
              </thead>
              <tbody>
                {order.books.map(b => (
                  <tr key={b.id}>
                    <td>{b.id}</td>
                    <td>{b.title}</td>
                    <td>{b.author}</td>
                    <td>${b.price.toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        ))
      )}
    </div>
  );
}
