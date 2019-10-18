## 1.14.4

16th April 2019

- internal refactoring of a few methods related to rendering ports

## 1.14.3

15th April 2019

- added support for `is-source` and `is-target` on jtk-port elements.

## 1.14.2

12th April 2019

- Fixed issue with the packaging of the flowchart builder demonstration (the drop library was missing)

## 1.14.1

10th April 2019

- internal rewrite of the jsPlumbToolkitIO module from JS to Typescript
- internal rewrite of the Autosaver module from JS to Typescript
- expose `dropHandler` on JsPlumbToolkitPaletteComponent in the React integration
- update Community edition to 2.9.1

## 1.14.0

6th March 2019

- Documentation removed from the license/evaluation bundles and moved to https://docs.jsplumbtoolkit.com
- update Community edition to 2.9.0
- in direct render mode, honor any max-width or max-height CSS properties applied to the container.

## 1.13.8

5th March 2019

- documentation updates

## 1.13.7

4th March 2019

- documentation updates
- verified that the Toolkit works with Angular 7
- inclusion of example package.json for Angular 7 applications

## 1.13.6

3rd March 2019

- add grunt as a dev dependency to package.json in the evaluation/license downloads
- update installation docs to show correct npm commands needed to run demonstration server
- added a Vue 2 mixin wrapper around the Drop Manager
- added a React component to wrap the Drop Manager
- added an Angular component to wrap the Drop Manager
- added drop on edge behaviour to Flowchart demo, plus the Vue/React/Angular versions
- fixed an issue that was preventing a node/group that had a loopback edge from being recognised as a valid root for
 the hierarchical layout.
 - removed `allowDropOnCanvas`, `allowDropOnNodesAndGroups` and `allowDropOnEdges` parameters from the Drop Manager. These
 are inferred now from the presence of the associated `on**Drop` methods.

## 1.13.5

1st February 2019

- bugfix for addFactoryNode method on Toolkit class: `data` was ignored when calling the method with a callback.

## 1.13.4

13th December 2018

- minor tweak to the community edition ingest code

## 1.13.3

11th December 2018

- upgrade to Community edition 2.8.6.

## 1.13.2

7th December 2018

- improved community ingest support: endpoints are now modelled as ports on their parent nodes. 

## 1.13.1

6th December 2018

- added support for drop on edge to Drop Manager
- documented the Drop Manager
- updated the Spring layout to correctly honour any `padding` value it is given
- support user-specified number of iterations for magnetize to run on layouts.
- upgrade to Community edition 2.8.5.

## 1.13.0

29th November 2018

- updated Vue integration to provide an ES6 module package 
- updated Vue integration to allow Vue components to be used as node/group renderers
- removed the `jsplumb-toolkit` Angular component. This has been deprecated for a while and unused in any of the jsPlumb demonstrations.
 Use `jsplumb-surface` instead, and create instances of the Toolkit on the jsPlumb Angular service.


## 1.12.0

26th November 2018

- added support for "spacing" to the constructor params for the Hierarchical layout. See hierarchical layout documentation for a discussion.
- deprecated `compress:true` on Hierarchical layout. Use `spacing:"compress"` instead.
- Surface widget consumes the mouse events for pan/pinch zoom now. Fixes an issue seen on iPads where these operations
 were causing the page to begin to scroll.
 - fixed issue with dialog loading in Vue demo
 - improved handling of individual Selection rendering


## 1.11.9

16th November 2018

- fix for duplicated nodes appearing in miniview when ingesting a Community edition instance
- update the surface component to better support rendering the contents of individual groups
- added a cache for node/port/edge/group definitions. Since these cannot be reloaded anyway, we can safely cache them
after compiling them for the first time.

## 1.11.8

4th November 2018

- Upgrade to Community edition 2.8.4.

## 1.11.7

2nd November 2018

- Fixed an issue causing edges whose type inherited from some other type to sometimes fail to update properly after updateEdge called.

- added support for "vertexId.portId" format for ports in the default json loader.

- updated IO docs.

## 1.11.6

28th October 2018

- Upgrade to Community edition 2.8.3. Fixes an intermittent issue with continuous anchors when edges are relocated to
a new element.

## 1.11.5

26th October 2018

- Updates to the Community edition ingest mechanism:

    - default to Absolute layout for ingested Community edition instances
    - ensure layout position for newly ingested elements is correct
    - support optional `data` to be stored with each newly ingested element
    - the `ingest` method on the Surface component now handles appending the element to the underlying canvas.    

- Upgrade to Community edition 2.8.2
- add `setEnabled(boolean)` method to jsPlumbToolkitDragManager.

## 1.11.4

18th October 2018

- update the event args passed by a Selection's reload method to be in line with those passed by a Toolkit instance
- if no container specified in render call, throw an Error. This is easier to debug.
- minor updates to drawing tools - an `onEdit` callback is supported, and `reset` is exposed on the drawing tools object.
- added support for 'align' to the Hierarchical layout, to alter how child groups are positioned with respect to their parent nodes

## 1.11.3

6th October 2018

- upgrade to Katavorio 1.1.0, to fix an issue that was causing multiple unnecessary nodeMoveEnd events to fire
 when a selection was dragged.
 
## 1.11.2

29th September 2018

- minor updates to the React typings
- updates for the React documentation

## 1.11.1

25th September 2018

- added Typescript definition file for the React integration.
- added Angular and React "skeleton" demonstrations - simple demonstrations designed to assist in getting something running.

## 1.11.0

24th September 2018

#### Breaking

- `jsPlumbToolkitUtil` folded into `jsPlumbUtil` - including the Selection class. Only a breaking change if your code
uses `jsPlumbToolkitUtil` directly of course.

#### Non-breaking

- Updates to the React integration to support using React components to render nodes/groups.
- Added optional undo/redo support package for Node/Group/Edge/Port add/remove operations.i
- Angular integration now supports referencing components directly in the view, instead of having to resolve them
via a `nodeResolver`
- Added an Angular version of the Database Visualizer demonstration
- Documentation updates
- Updated a couple of demonstrations to show usage of undo/redo
 

## 1.10.0

12th September 2018

- precompile React JSX components and package those instead of the original JSX files.  This was done because of a
compatibility issue with applications made with create-react-app: they do not compile JSX files outside of the `src`
directory.
- Remove unnecessary duplicated React demonstration
- Update React demonstration and docs to reflect changes in the React integration packaging.

## 1.9.2

7th September 2018

- added some more information to the Typescript typings file.
- fix in issue on Firefox Quantum with the drop manager.

## 1.9.1

6th September 2018

- Added `portOrderProperty` to the Toolkit to assist in automatically setting the order of ports in some node.

## 1.9.0

31st August 2018

- Upgrade to Community edition 2.8.0, which contains Mottle 1.0.0, which doesn't use document.createTouch/document.createTouchList, as these methods are not supported
in latest Chrome and are becoming obsolete in all browsers. If you cannot upgrade to this version of jsPlumb and you're finding problems
in Chrome on touch devices, there are shims available in the Mottle project on Github.

- minor updates to the Database Visualizer demonstration, to make it more usable on touch devices.

## 1.8.9

28th August 2018

- upgrade to Community edition 2.7.19
- update Angular docs to provide some tips on how to integrate with an existing application.


## 1.8.8

27th August 2018

- catch exceptions when trying to install and Typescript or Angular is not found.
- update code to work around a Typescript issue causing Angular CLI 6 production builds to fail.

## 1.8.7

24th August 2018

- pass node and group elements in callback to group:addMember and group:removeMember events from Surface.
- update to the Angular port renderer
- basic support for nodes with multiple parents in the Hierarchical layout.
- add jtk-most-recently-dragged class to the most recently dragged node/group in the Surface

## 1.8.6

20th August 2018

- improved rendering of Ports in Angular integration
- added `animateToSelection` method to Surface
- fixed issue with `zoomToSelection` ignoring Groups.
- added `getEdges` method to Selection
- fixed documentation error that was causing `Selection.edgeEach` to be excluded.
- upgrade to community edition 2.7.16

## 1.8.5

15th August 2018

- added `updateGroup` method to Toolkit

## 1.8.4

11th August 2018

- added a fix for the inverse lasso on IE11
- upgrade to community edition 2.7.15

## 1.8.3

10th August 2018

- added `getGroups` method to Toolkit class.
- added `layout` entry to SurfaceOptions type definitions.
- added `getPort(..)` to Node type definition
- added `source` and `target` to Edge type definition
- fixed an issue with node removal in the miniview
- when removing a group from the UI and retaining its child nodes, the child nodes are drawn in the same page position as they
were in when inside the group, and no relayout is called.
- upgrade to Community edition 2.7.14

## 1.8.2

4th August 2018

- added the concept of `portDataProperty`, a simple way to replace `portExtractor` and `portUpdater`.

## 1.8.1

25th July 2018

- changed the format of some method declarations that were causing Angular's optimizer to confuse itself when running a production build.
- upgrade to Community edition 2.7.12
 
## 1.8.0

25th July 2018

- Added support for layouts to groups. Groups have an 'absolute' layout by default but can support any layout.
- fixed a few minor issues related to viewport size that were affecting the zoom to extents calculations.
- removed 'autoSizePadding' parameter from group definitions. Preferred method now is CSS.
- toolkit now fires a `group:addMember` event if a node added via `addNode` that has a group reference in it.
- fixed an issue in the Vue2 integration whereby user supplied dragOptions were ignored.
- updated all Angular demos to include the required polyfills for them to work with IE11.
- updated to Community edition 2.7.10

## 1.7.9

26th June 2018

- added support for 'invert' flag on Hierarchical layout - puts the root node at the bottom for horizontal layouts, or on the
right for vertical layouts.

## 1.7.8

24th June 2018

- updated to Community edition 2.7.9

## 1.7.7

22nd June 2018

- updated to Community edition 2.7.8
- additions to Typescript typings file
- group:addMember and group:removeMember events optionally include the other group involved when the event was fired as the result of
  a member changing groups.
- updated the Surface to restore the current zoom, pan location and transform origin in the `loadState` method

## 1.7.6

21st June 2018

- updated to Community edition 2.7.7
- updated view documentation and database visualizer application to take out allowLoopback and allowNodeLoopback flags. These are supported,
 but not in a view, only in a model. 

## 1.7.5

17th June 2018

- updated to Community edition 2.7.5

## 1.7.4

16th June 2018

- updates to documentation 

## 1.7.3

14th June 2018

- fixed an issue where after `autoSizeGroups` had been called, the new group sizes were not known by the surface widget,
causing zoomToFit to fail.

## 1.7.2

14th June 2018

- `locationFunction` in the Absolute layout is now given the current group as the second argument, when it is called 
during a group's layout of its child nodes.

## 1.7.1

27th May 2018

- upgrade to Community edition 2.7.3

## 1.7.0

25th May 2018

- upgrade to Community edition 2.7.2
- consolidated the various layout demonstrations into a single demonstration

## 1.6.18

16th May 2018

- added typings information for jsPlumbToolkitIO
- upgrade to Community edition 2.6.12

## 1.6.17

30th April 2018

- updates to the Typescript typings files
- moved `BaseNodeComponent` out of the angular demo code and into the core Toolkit Angular integration
- reworked the Angular 4/2 demonstration to include the Angular router.
- added Angular 5 demonstration
- fixed an issue in `BaseNodeComponent` where the component did not clean itself up properly.

## 1.6.16

17th March 2018

- fix for nodes being made visible having edges to non visible nodes. previously these edges would be made visible.

## 1.6.15

16th March 2018

- fix for the `isVisible` test on nodes
- added `zoomToVisible` method to the Surface widget
- added `zoomToVisibleIfNecessary` method to the Surface widget
- upgrade to Community edition 2.6.9
- added optional `filter` parameter to `zoomToSelection` method on the Surface widget.
- added `sizeGroupToFit(group)` method to Surface widget

## 1.6.14

9th March 2018

- upgrade to Community edition 2.6.8
- added support for multi selects to the Dialog helpers.
- Node factories and Group factories now return the newly created values.

## 1.6.13

8th February 2018

- upgrade to Community edition 2.6.7
- upgrade to Katavorio 0.25.0

## 1.6.12

4th February 2018

- fixed issue causing the community instance rendering content to be detached from the Surface if `clear` called before data loaded.

## 1.6.11

2nd February 2018

- upgrade to Community edition 2.6.5
- upgrade to Katavorio 0.24.0
- Added a CSS style rule to the defaults css to suppress the pointer events on an element that was created by Katavorio 
in response to a drag in which the element should first be cloned. 
 
## 1.6.10

27th January 2018

- updates to the Angular system js demo to bring it back to working

## 1.6.9

26th January 2018

- The Toolkit's Typescript typings file now includes the type information for the Community edition, exported as module `jsPlumb`
- Typescript typings added to `package.json` and included in built `.tgz` package

## 1.6.8

26th January 2018

- typescript .d.ts updates
- upgrade to Community edition 2.6.3

## 1.6.7

24th January 2018

- upgrade to Community edition 2.6.2
- vast performance improvement in the `clear` method of the Toolkit.

## 1.6.6

20th January 2018

- upgrade to Community edition 2.6.0
- documentation updates
- Typescript .d.ts updates
- rename `generator` input on Angular `jsplumb-palette` component to `dataGenerator`, which is the name that the docs discuss.

## 1.6.5

14th January 2018

- upgrade Community edition to 2.5.13
- fix for the setViewportCenter method of the Surface widget
- fix to ensure magnetize routine is aware of node size changes
- update docs to remove outdated references to refreshNode, refreshPort, refreshEdge and their related nodeRefreshed, portRefreshed, edgeRefreshed events. 

## 1.6.4

29th December 2017

- improved location calculation for nodes dragged and dropped from a node palette.

## 1.6.3

23rd December 2017

- fix overzealous event consumption

## 1.6.2

21st December 2017

- upgraded to Community edition 2.5.10
- fix for the case that the surface widget is inside some element that has been scrolled; drop on canvas and zoom were
deriving incorrect values for the mapped location on the canvas.

## 1.6.1

13th December 2017

- added experimental support for using the mouse wheel to pan (set `wheelPan:true` on a render call)
- upgraded to Community Edition 2.5.9

## 1.6.0

8th December 2017

- added new layout type "Balloon". Arranges nodes in clusters.
- added HIDDEN and COLOR to supported input types for Dialogs
- exposed `apply` and `extract` methods on Dialogs to allow users to bind data object to DOM manually.
- upgrade to Community edition 2.5.8. Fixes an issue with disappearing connectors after `updateEdge` called. 

## 1.5.12

12th November 2017

- Added optional basic connection debugging to the Surface widget.

## 1.5.11

7th November 2017

- Minor updates to the Angular Groups demo page.

## 1.5.10

1st November 2017

- Upgrade to Rotors 0.3.18, which contains a fix for the `r-html` tag.

## 1.5.9

29th October 2017

- Miniview hides/shows nodes when they are hidden/shown in its associated Surface.

## 1.5.8

22nd October 2017

- upgrade to Community edition 2.5.7. 
- suppress node dragging when user is pinch-zooming.

## 1.5.7

18th October 2017 

- added support for mouse events to `jtk-source` and `jtk-target` elements that are declared as Ports (via the existence of
an `port-id` attribute). Previously these elements could only be mapped to mouse events if they were declared at a Node scope.

- updated bundled Community edition to 2.5.6, which includes a minor fix for class name manipulations on SVG elements in Chrome.

## 1.5.6

11th October 2017

- minor updates to the layout magnetization engine

## 1.5.5

8th October 2017

- upgrade to community edition 2.5.5
- fix an issue with the Spring layout failing to treat `left:null` and/or `top:null` as an absence of value in the positioning data,
when absolute backing is switched on.

## 1.5.4

4th October 2017

- upgrade to Community edition 2.5.2. Fixes an issue with dragging to any grid size other than [10,10].

## 1.5.3

6th September 2017

- No longer refresh the entire layout each time a new Edge is added. Add support for `refreshLayoutOnEdgeConnect` as a parameter on
the `render` call, to allow users to override this behaviour.
- Angular 1.x revalidates a node after new edge added, to prevent errors resulting from the asynchronous nature of node painting.
- Add more methods to the Toolkit typings file
- Switched to a more consistently successful way of importing toolkit typings in package.json.

## 1.5.2

29th August 2017

- Added support for `autoSizePadding` to Group definitions.

## 1.5.1

28th August 2017

- Added support for optional `endpoint` attribute on `jtk-source` and `jtk-target` elements. If present, an endpoint
 is created for the node instead of its entire element being converted into a source or target. This is the equivalent of
 a `jtk-port` element in that it creates an Endpoint, but connections are made directly on the Node.
- Upgraded community edition to 2.5.1 (provides fix for an issue with overlays disappearing when calling `setType` on an Edge)
- Added more methods to the Toolkit typings file

## 1.5.0

22nd August 2017

- added `originalData` to the params passed back to `nodeUpdated`, `portUpdated` and `edgeUpdated` events.
- upgraded CLI version to 1.3.0 in the Angular demos
- fix for the case that a new edge is added during the `edgeAdded` callback of the Surface class; the edge was being
added to the model but the UI was not rendering it.
- upgrade Community edition to 2.5.0

## 1.4.7

12th August 2017

- fix issue with safari zoom operating backwards to all other browsers

- add `portUpdater` - an optional function that is called when an update occurs to a Port.

## 1.4.6

28th July 2017

- fixed issue with rendering in Angular 4/2 Node components. The initial data was not being applied prior to the Toolkit
checking a rendered Node's dimensions.

- code documentation updates

- improved the accuracy of the method by which the Toolkit determines that Nodes dragged from a palette have been dropped on a Group.

- package Angular 4 integration as a `tgz`, for inclusion in package.json.

- add support for Groups to the active filtering support in the Surface.

## 1.4.5

18th July 2017

- added `wheelReverse` option to Surface widget.

## 1.4.4

14th July 2017

- support custom export type in syntax highlighter

## 1.4.3

10th July 2017

- added getViewportCenter method to Surface widget
- the `graphCleared` event is now fired _after_ the graph is emptied, and before the graph is emptied, when `graphCleared`
used to be fired, `graphClearStart` is fired instead.

## 1.4.2

23rd June 2017

- fix an issue where hiding a connection that was attached to an endpoint that has other connections causes those
other connections to be hidden too.

## 1.4.1

20th June 2017

- upgrade Rotors to 0.3.17
- upgrade Community edition to 2.4.3

## 1.4.0

15th June 2017

- fix to get the Surface class correctly exported from `jsplumbtoolkit`
- workaround for AOT issue when lifecycle methods declared on superclasses
- use Angular CLI as the default for Angular demos
- fix issue with an edge dragged from one endpoint to another - the target was being 
incorrectly set as the Node on which the endpoint resided, not the endpoint's associated Port.

## 1.3.4

25th May 2017

- upgrade to community edition 2.4.2
- fix link to angular integration docs

## 1.3.3

10th May 2017

- updated to Rotors 0.3.16

## 1.3.2

10th May 2017

- updated the code that handles a moved connection to ensure it treats Ports correctly.

## 1.3.1

9th May 2017

- remove all String refs from React demo and from docs for React demo
- minor documentation updates

## 1.3.0

8th May 2017

- Upgrade to Community edition 2.4.0
- Support Angular 4 (when we say 'Angular' now, we mean Angular 4. Angular 2 and Angular 1.x are labelled specifically).
- Upgrade to Webpack 2 and related grunt-webpack in React and Webpack demo pages
- replaced String refs in React integration with the callback pattern.
- Added Angular CLI version of the Angular 4 flowchart demo
- Added support for onDrop to the `jsplumb-palette` directive in the Angular 1.x integration.


## 1.2.16

30th April 2017

- group auto size now correctly takes into account the group's drag area
- when adding a node to a group, if left/top properties are set in the data, they are used as the node's position.
- fixed an issue with groups not being cleared properly from the underlying Community instance when using the `clear` method

### 1.2.15

26th April 2017

- update to version 15.5.0 in the React integration demo.

### 1.2.14

24th April 2017

- added support for onBeforeAutoSave and onAfterAutoSave optional functions to Toolkit's auto save functionality.

### 1.2.13

24th April 2017

- upgrade to Community edition 2.3.5
- nodes dropped on groups now assigned their correct position

### 1.2.12

20th April 2017

- fix issue with `data` not being exported on edges whose source is a Group

### 1.2.11

8th April 2017

- Fixed issue with source element creating multiple connections after drag.

### 1.2.10

30th March 2017

- added support for `magnetize` option in `dragOptions` of `render` call. Instructs jsPlumb to adjust a dragged Node/Group so
 that it does not overlap any other Nodes/Groups(at drag end).

### 1.2.9

22nd March 2017

- upgrade to Community edition 2.3.2 (which includes Katavorio 1.19.2)

### 1.2.8

16th March 2017

- upgrade to Community edition 2.3.1
- update to ensure that endpoints/anchors in Port definitions correctly override endpoints/anchors in Edge definitions.

### 1.2.7

8th March 2017

- Updated Angular 2 integration to force a render of each node as it is created. This ensures that any `jtk-port`,
`jtk-source` or `jtk-target` elements are fully rendered before we try to process them.

### 1.2.6

8th March 2017

- added support for `isEndpoint` flag in port definitions in the view. Causes jsPlumb to use an Endpoint instead of making
an entire element a source/target.

- added support for `jtk-port`, `jtk-source` and `jtk-target` elements to Group templates.

- updated the Surface widget to correctly render `isEndpoint` Ports when new Ports are added programmatically to a Toolkit instance.

- updated the Angular docs with a short discussion on the perils of the asynchronous nature of Angular's rendering cycle.


### 1.2.5

1st March 2017

- added support for `anchor-x`, `anchor-y`, `orientation-x`, `orientation-y`, `offset-x` and `offset-y` attributes in the `jtk-port` element.

### 1.2.4

27th February 2017

- updated the `exportData` method to correctly export edges between Groups.

### 1.2.3

27th February 2017

- add grunt to webpack package json

### 1.2.2

27th February 2017

- fix issue with preinstall script when running on Windows

### 1.2.1

21st February 2017

- `setPosition` method of Surface widget now works for Groups too
- `animateToPosition` method of Surface widget now works for Groups too.
- `toolkit` and `renderer` are now passed as arguments to any events defined in your views.

### 1.2.0

16th February 2017

- Upgrade to Community edition 2.3.0
- Community edition is now bundled into the Toolkit edition - no separate import is required.
- addition of Angular 2 integration support
- addition of Webpack bundling example
- addition of React integration support
- addition of Vue 2 integration support
- npm pack of the Toolkit code is shipped with licenses
- documentation updates
- fixed issue that was preventing deselection of Group elements
- fixed issue with update of moved nodes in miniview.
- fixed issue with duplicate Nodes being allowed inside Groups
- `doNotFireEvent` parameter removed from Toolkit's `addToGroup` method.
- fixed issue with Groups not being removed from DOM on clear.
- Surface widget fires `lasso:end` event on lasso mouseup. No arguments are passed to the callback method.


### 1.1.6

3rd January 2017

- added getClusters method to Toolkit and to Graph

### 1.1.5

20 December 2016

- upgrade to Community edition 2.2.8

### 1.1.4

2 December 2016

- added `getEdgeAt` method to Path
- added support for custom buttons on dialogs.

### 1.1.3

29 November 2016

- documentation updates for Hierarchical layout

### 1.1.2

28 November 2016

- documentation updates.

### 1.1.1

27 November 2016

- upgrade to Community edition 2.2.6

- `setAbsolutePosition(el, xy[])` removed from the Surface widget. Use `setPosition(el, x, y)` instead. `setAbsolutePosition` is
still used by Decorators.

- Nodes added programmatically to Groups (via the Toolkit's `addToGroup(Group, Node)` method) are placed in the center of
 the Group's drag area. Previously no change was made to their offset and this more often than not resulted in the Node
 not appearing inside the Group.
 
- upgrade to Rotors 0.3.12

- support HTTP headers in `save` method and in auto save functionality.


### 1.1.0

28 October 2016

- added support for Groups

- added support for multiple root nodes in hierarchical layout

- aliased the `getEdge` method in Selection as `getEdgeAt`; this is more consistent with the API to access Nodes/Groups.

- support for custom template resolver in `render` call

- support for templates provided as a map of string s in `render` call.

- added a couple of helper methods to the jsPlumbToolkitInstance class for working with Edges:

    selectAllEdges  : Gets all edges in the Toolkit instance as a Selection object.
    
    addAllEdgesToSelection : Adds all the Edges in the Toolkit instance to the Toolkit's current selection.
    

### 1.0.31

18 October 2016

- fix issue with ingestNode method causing miniview to fail with ingested community instance.
- ensure ingested nodes are registered with the layout.


### 1.0.30

20 September 2016

- fix issue that was causing a loopback to be added twice to the object it belonged to (although not to the Toolkit itself)

### 1.0.29

6 September 2016

- update to jsPlumb 2.1.7, containing a few minor connection fixes.

### 1.0.28

29 August 2016

- further small fix to whitespace rendering

### 1.0.27

28 August 2016

- fixed issue with whitespace rendering inside templates (in some cases whitespace was being trimmed when it should not
have been)

- added setSourceEnabled, setTargetEnabled, setEnabled methods to Surface widget

### 1.0.26

18 August 2016

- fixed issue with `setVisible` method on the Surface widget, in which connections from some element were made visible
when the element was made visible, even if their other element was not currently visible.

### 1.0.25

12 August 2016

- upgraded Community edition to 2.1.5, which contains Mottle 0.7.3, which itself has a fix for the synthesized tap
event on Safari and Firefox.

### 1.0.24

8 July 2016

- upgrade to community jsPlumb 2.1.4
- reinstate missing apps (hello world, skeleton app)

### 1.0.23

19 May 2016

- upgrade to jsPlumb Community 2.1.2, which contains a couple of bugfixes for click events and for overlays that were not
being removed when switching types.

### 1.0.22

6 May 2016

- upgrade to jsPlumb Community 2.1.1
- fix for an operator precedence issue in the default object factory that was causing the object's type to be forgotten.
- upgrade to Rotors 0.3.11 (fix for deeply nested template rendering)
- fix for default JSON parser when Ports that have no nodeId are present.

### 1.0.21

21 April 2016

- api documentation fixes
- upgrade to jsPlumb community 2.1.0 (a couple of memory leak issue fixes)
- upgrade to Rotors 0.3.10
- upgrade to Farahey 0.7
- memory leak fixes
- fix issue with miniview not repainting after expansion

### 1.0.20

17 March 2016

- update Rotors to fix possible infinite loop with nested templates

### 1.0.19

9 March 2016

- update Rotors

### 1.0.18

9 March 2016

- fixed an issue with the auto save: it was not suspended during data load.

### 1.0.17

8 February 2016

- fix for inverse lasso: the mask does not appear until the user starts to drag.

### 1.0.16

1 February 2016

- fix for state deactivate - parameterised overlays now retain their values.

### 1.0.15

25 January 2016

- internal build script update to community 2.0.6

### 1.0.14

24 January 2016

- update to Rotors 0.3.7, which contains a fix for parameterised attributes on custom elements, and support for an
'updated' callback in custom tags.

- add support for `inverted` mode to lasso, in which unselected parts of the UI are masked, rather than the default
behaviour of drawing an element to represent the selected area.

- update community jsPlumb to 2.0.6. Not a required update for 1.0.14; 2.0.5 is ok. 

### 1.0.13

11 January 2016

- getAllEdges in jsPlumbToolkit no longer takes parameters, and actually works properly now.
- added getAllEdges to Graph class.
- small tidy up in Angular demo templates to fix IE* rendering issue.

### 1.0.12

14 December 2015

- Update to Rotors to support attributes with colons (such as `xlink:href` on an svg image)

- Fix updateNode bug: nodes were not being removed from previous posses before being possibly added to new ones.

- Posses are assigned to elements in bulk after load, in case one of them has tried to access a node that is not yet 
loaded.

- Added `batch` function to Surface widget, to run a function while rendering and events are both suspended.

- Added support for rectangular tiles in the tiled background of a Surface widget

- Fixed issue that caused an error when dragging an existing edge from one source to another.

- `zoomToFit` on the Surface widget now defaults to filling 90% of the viewport. This can be overriden by supplying
a `fill` parameter:

```javascript
surface.zoomToFit({fill:0.75});
```

- The link to `filters` in the documentation no longer gives a 404.
 
- Fixed an issue that was occasionally causing multiple buttons in dialogs.

- Fixed an issue that was causing dynamically populated overlays to revert when a new State was applied.

- Introduced the concept of `typeProperty` (and `edgeTypeProperty` / `portTypeProperty`). This reflects the fact that
for the majority of applications, the function used to determine some object's `type` simply looks at the value of some
given property. By providing the name of this property, rather than a `typeFunction`, the Toolkit is then able to write
an object's type as well as read it.

- Added support for setType method

- Added small fix to avoid buttons being added more than once to a dialog.


### 1.0.11

23 October 2015

- upgrade to Community edition 2.0.4. Contains a couple of changes to the way posses are handled, and the
 ability to drop elements onto other elements. Also contains a fix for the computation of offsets when dragging 
 nested elements.
- update to Rotors to support attributes with colons (such as `xlink:href` on an svg image)
- update to Rotors to include `<r-html>` tag (for embedding HTMl rather than plain text)
- change pan/zoom widget so it no longer consumes all mouse events it does not process. 
- `jsPlumbToolkitUtil.ajax` takes optional `headers` map (for setting http headers).
- `jsPlumbToolkitInstance.load` takes optional `headers` map (for setting http headers).
- `jsPlumbToolkitInstance.load` sets `Accept:application/json` HTTP header if headers not supplied. 


### 1.0.10

16 October 2015

- added `adHocLayout` method to Surface widget. This lets you run a named layout one time on your data.


### 1.0.9

16 October 2015

- upgrade to Community edition 2.0.3 (changes to Posse dragging, minor bugfixes)

- Removed a number of spurious animations that were unnecessarily occurring while the Surface widget initialised a paint.


### 1.0.8

12 October 2015

- Upgrade to Community edition 2.0.2 (for documentation improvements)


### 1.0.7

7 October 2015

- Upgrade to Community edition 2.0.1
- Documentation improvements


### 1.0.6

6 October 2015

- upgrade to Community edition 2.0.0
- upgrade to Rotors 0.3.5
- nodeUpdated/edgeUpdated/portUpdated events are given the updates that the user passed in (issue 61)
- beforeConnect interceptor gets edgeData as third argument (if some data was returned from beforeStartConnect) (issue 62)
- Add the ability to 'refresh' the set of nodes registered via a `registerDroppableNodes` call on a Surface.
- Add `centerOnAndZoom` function to `Surface`
- `modelLeftAttribute` and `modelTopAttribute`, which specify the left/top attribute against which to store node
positions, now support dotted notation, ie. can refer to a nested element.
- Added `zoomToSelection` to Surface widget.

### 1.0.5

31 August 2015

- Reinstate animation when panning the surface
- Expose `setPan` method on the surface
- Support animation in the `setPan` method
- Support enableAnimation:false flag in `render` method. default is true.
- Upgrade to Community edition 1.7.10 (bugfix for animation when providing 0 as param value)


### 1.0.4

29 August 2015

- Several improvements to the ingest functionality, including the ability to subsequently ingest new elements.
- Upgrade to Community edition 1.7.9

### 1.0.3

21 August 2015

- Fix loading issue with directed edges in basic JSON parser.
- Add setDirected method to Edge
- Add tracePath method to Renderer
- Add Path Tracing demo

### 1.0.2

10 August 2015

- Add `traversePath` method to Surface (and `animateOverlay` method to Connection)
- Upgrade to Rotors 0.3.0
- Fix issue in Hierarchical layout in which circular references in the hierarchy cause an infinite loop.

### 1.0.1

1 August 2015

- Addition of fixElement and floatElement methods to pan/zoom widget and Surface, and exposing of these methods
 to the layout decorator functionality.

### 1.0.0

19 July 2015

Initial release.
