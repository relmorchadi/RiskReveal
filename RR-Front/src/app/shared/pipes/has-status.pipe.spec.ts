import { HasStatusPipe } from './has-status.pipe';

describe('HasStatusPipe', () => {
  it('create an instance', () => {
    const pipe = new HasStatusPipe();
    expect(pipe).toBeTruthy();
  });
});
