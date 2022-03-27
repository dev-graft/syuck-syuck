import React from 'react';
import { render, screen } from '@testing-library/react';
import GoodWordPage from './pages/goodWord/GoodWordPage';

test('renders learn react link', () => {
  render(<GoodWordPage />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
