# Toolkit Edition

### Typescript

In the long term the aim is to convert the codebase to Typescript. To that end there is a `ts` directory containing a few files; at the time of
  writing only `editable-flowchart.ts` is being built for inclusion in the Toolkit. `jsplumbtoolkit-angular.ts` is built separately; see below.
  
We currently have to build to ES5, not ES6, which limits what we can do inside the typescript source. We want to build to ES6 and use Rollup. For now
  we build to ES5 and create separate, standalone, files, that have no import declarations.
  
### Angular 2/4/5 integration

This is in the file `ts/jsplumbtoolkit-angular.ts`.  The build target `build-angular` copies this file to `dist-angular`, in which there is a
`tsconfig.json` that is suitably configured for Angular (with the experimental decorators flags switched on).

### Building

There are a few bits and pieces that have to be built:

- all of the editable connector stuff is written in Typescript and currently built via `tsc`, with the output going into the `_build` directory.
This is not done

### Building - Future state

The goal is:

- Convert everything, including the community edition, into Typescript. Use rollup to build a complete Toolkit bundle, but
also include all the individual files in a release, so that licensees can import only the things they need. This will of course
 require a certain amount of refactoring of the source.
 
 - Stop using Grunt. There is no point in using Grunt now that npm can do everything. Although some of the copy tasks may need
 to be recreated in helper scripts or something.
 
 - Find a way to sort out the problem with the built util module. We use Rollup and create a "umd" module, which is purported to
 function as either an IIFE, a CommonJS module, or an AMD module, but in reality when using Webpack we do not end up with
 the `jsPlumbUtil` object available in the root context.
 
