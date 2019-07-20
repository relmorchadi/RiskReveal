import {Input} from './input';
import * as Types from './types';

export const Actions = {
  updateKey: {
    type: Types.updateKey,
    handler: (rightMenuInput: Input, key, value): Input => ({
      ...rightMenuInput,
      [key]: value
    })
  }
}
