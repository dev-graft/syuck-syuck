import { fireEvent, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import CounterPage from './CounterPage';

describe('Counter test', () => {
  it('should render Counter', () => {
    render(<CounterPage />);

    const target = screen.getByRole('button', { name: '+' });
    fireEvent.click(target);
    userEvent.click(
      target,
    ); /** userEvent 사용 권장 (내부적으로 fireEvent를 사용하며, 기능이 더 많음)**/

    expect(screen.getByText('2')).toBeTruthy();
  });
});
